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

import org.mercuriusb.collectio.dto.BookmarkMetaDataDto;
import org.mercuriusb.collectio.model.BookmarkUserMetaData;
import org.mercuriusb.collectio.service.BookmarkMetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;


@Path("/api/v1/bookmarkmetadatas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookmarkMetaDataResource{

  private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkMetaDataResource.class);
  private final BookmarkMetaDataService service;

  // constructor injection
  public BookmarkMetaDataResource(BookmarkMetaDataService bookmarkmetadataService){
    this.service = bookmarkmetadataService;
  }

  @GET
  @Authenticated
  @Operation(summary = "Returns all existing bookmarkmetadatas")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get All BookmarkMetaDatas",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.ARRAY, implementation = BookmarkUserMetaData.class))),
          @APIResponse(
              responseCode = "404",
              description = "No bookmarkmetadatas found",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getAllBookmarkMetaDatas(@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Returns an bookmarkmetadata given the bookmarkmetadata Id")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get BookmarkUserMetaData by bookmarkmetadataId",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = BookmarkUserMetaData.class))),
          @APIResponse(
              responseCode = "404",
              description = "No BookmarkUserMetaData found for the bookmarkmetadataId provided",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getBookmarkMetaDataById(@PathParam("id") Long id,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    BookmarkMetaDataDto dto = service.getById(id);
    if(dto != null){
      LOGGER.info("Found bookmarkmetadata {}", dto);
      return Response.ok(dto).build();
    }else{
      LOGGER.debug("No bookmarkmetadata found with id {}", id);
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @POST
  @Authenticated
  @Operation(summary = "Creates a new bookmarkmetadata")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "201",
              description = "BookmarkUserMetaData created successfully",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = BookmarkUserMetaData.class))),
          @APIResponse(
              responseCode = "400",
              description = "BookmarkUserMetaData already exists with bookmarkmetadataId",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response createBookmarkMetaData(@RequestBody(required = true) @Valid BookmarkMetaDataDto bookmarkmetadata,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    service.create(bookmarkmetadata);
    URI bookmarkmetadataUrl = URI.create("/api/v1/bookmarkmetadatas/" + bookmarkmetadata.id());
    LOGGER.info("New bookmarkmetadata added at URL {}", bookmarkmetadataUrl);
    return Response.created(bookmarkmetadataUrl).build();
  }

  @PUT
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Updates an existing bookmarkmetadata")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "BookmarkUserMetaData successfully updated",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = BookmarkUserMetaData.class))),
          @APIResponse(
              responseCode = "404",
              description = "No BookmarkUserMetaData found for bookmarkmetadataId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response updateBookmarkMetaData(@PathParam("id") Long id, @RequestBody @Valid BookmarkMetaDataDto bookmarkmetadata,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    BookmarkMetaDataDto dto = service.getById(id);
    if(dto == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    dto = service.update(id, bookmarkmetadata);
    LOGGER.info("BookmarkUserMetaData with id {} updated successfully", id);
    return Response.ok(dto).build();
  }

  @DELETE
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Deletes an existing bookmarkmetadata")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "204",
              description = "BookmarkUserMetaData successfully deleted",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = BookmarkUserMetaData.class))),
          @APIResponse(
              responseCode = "404",
              description = "No BookmarkUserMetaData found for bookmarkmetadataId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response deleteBookmarkMetaData(@PathParam("id") Long id,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    if(service.delete(id)){
      LOGGER.info("BookmarkUserMetaData deleted with id {}", id);
      return Response.noContent().build();
    }
    ;
    return Response.status(Response.Status.BAD_REQUEST).build();
  }
}