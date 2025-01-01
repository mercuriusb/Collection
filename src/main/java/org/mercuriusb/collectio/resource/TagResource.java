package org.mercuriusb.collectio.resource;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import org.mercuriusb.collectio.dto.tag.TagDto;
import org.mercuriusb.collectio.model.Tag;
import org.mercuriusb.collectio.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;


@Path("/api/v1/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource{

  private static final Logger LOGGER = LoggerFactory.getLogger(TagResource.class);
  private final TagService service;

  // constructor injection
  public TagResource(TagService tagService){
    this.service = tagService;
  }

  @GET
  @Authenticated
  @Operation(summary = "Returns all existing tags")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get All Tags",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.ARRAY, implementation = Tag.class))),
          @APIResponse(
              responseCode = "404",
              description = "No tags found",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getAllTags(@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    return Response.ok(service.getAll(userId)).build();
  }

  @GET
  @Authenticated
  @Path("/browse/{path: .+}")
  @Operation(summary = "Returns all tag hierarchy")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get Tag hierarchy",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = Tag.class))),
          @APIResponse(
              responseCode = "404",
              description = "No tags found",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response browseTags(@PathParam("path") String path,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    return Response.ok(service.getTree(path, userId)).build();
  }


  @GET
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Returns an tag given the tag Id")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get Tag by tagId",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = Tag.class))),
          @APIResponse(
              responseCode = "404",
              description = "No Tag found for the tagId provided",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getTagById(@PathParam("id") Long id,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    try{
      TagDto dto = service.getById(id, userId);
      LOGGER.info("Found tag {}", dto);
      return Response.ok(dto).build();
    }catch(WebApplicationException e){
      LOGGER.error(e.getMessage(), e);
    }
    return Response.status(Response.Status.NOT_FOUND).build();
  }

  @POST
  @Authenticated
  @Operation(summary = "Creates a new tag")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "201",
              description = "Tag created successfully",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = Tag.class))),
          @APIResponse(
              responseCode = "400",
              description = "Tag already exists with tagId",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response createTag(@RequestBody(required = true) @Valid TagDto tag,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    TagDto resultTag = service.createIfNotExists(tag,userId);
    URI tagUrl = URI.create("/api/v1/tags/" + tag.getId());
    LOGGER.info("New tag added at URL {}", tagUrl);
    return Response.created(tagUrl).build();
  }

  @PUT
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Updates an existing tag")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Tag successfully updated",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = Tag.class))),
          @APIResponse(
              responseCode = "404",
              description = "No Tag found for tagId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response updateTag(@PathParam("id") Long id, @RequestBody @Valid TagDto tag,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    try{
      TagDto dto = service.update(id, tag, userId);
      LOGGER.info("Tag with id {} updated successfully", id);
      return Response.ok(dto).build();
    }catch(WebApplicationException e){
      LOGGER.error(e.getMessage(), e);
    }
    return Response.status(Response.Status.BAD_REQUEST).build();
  }

  @DELETE
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Deletes an existing tag")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "204",
              description = "Tag successfully deleted",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = Tag.class))),
          @APIResponse(
              responseCode = "404",
              description = "No Tag found for tagId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response deleteTag(@PathParam("id") Long id,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    if(service.delete(id,userId) > 0){
      LOGGER.info("Tag deleted with id {}", id);
      return Response.noContent().build();
    }
    return Response.status(Response.Status.BAD_REQUEST).build();
  }
}