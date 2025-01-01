package org.mercuriusb.collectio.resource;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import org.mercuriusb.collectio.dto.bookmarktype.BookmarkTypeDto;
import org.mercuriusb.collectio.model.BookmarkType;
import org.mercuriusb.collectio.service.BookmarkTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;


@Path("/api/v1/bookmarktypes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookmarkTypeResource{

  private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkTypeResource.class);
  private final BookmarkTypeService service;

  // constructor injection
  public BookmarkTypeResource(BookmarkTypeService bookmarktypeService){
    this.service = bookmarktypeService;
  }

  @GET
  @Authenticated
  @Operation(summary = "Returns all existing bookmarktypes")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get All BookmarkTypes",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.ARRAY, implementation = BookmarkType.class))),
          @APIResponse(
              responseCode = "404",
              description = "No bookmarktypes found",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getAllBookmarkTypes(){
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Returns an bookmarktype given the bookmarktype Id")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get BookmarkType by bookmarktypeId",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = BookmarkType.class))),
          @APIResponse(
              responseCode = "404",
              description = "No BookmarkType found for the bookmarktypeId provided",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getBookmarkTypeById(@PathParam("id") Long id){
    BookmarkTypeDto dto = service.getById(id);
    if(dto != null){
      LOGGER.info("Found bookmarktype {}", dto);
      return Response.ok(dto).build();
    }else{
      LOGGER.debug("No bookmarktype found with id {}", id);
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @POST
  @Authenticated
  @Operation(summary = "Creates a new bookmarktype")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "201",
              description = "BookmarkType created successfully",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = BookmarkType.class))),
          @APIResponse(
              responseCode = "400",
              description = "BookmarkType already exists with bookmarktypeId",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response createBookmarkType(@RequestBody(required = true) @Valid BookmarkTypeDto bookmarktype){
    service.create(bookmarktype);
    URI bookmarktypeUrl = URI.create("/api/v1/bookmarktypes/" + bookmarktype.getId());
    LOGGER.info("New bookmarktype added at URL {}", bookmarktypeUrl);
    return Response.created(bookmarktypeUrl).build();
  }

  @PUT
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Updates an existing bookmarktype")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "BookmarkType successfully updated",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = BookmarkType.class))),
          @APIResponse(
              responseCode = "404",
              description = "No BookmarkType found for bookmarktypeId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response updateBookmarkType(@PathParam("id") Long id, @RequestBody @Valid BookmarkTypeDto bookmarktype){
    BookmarkTypeDto dto = service.getById(id);
    if(dto == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    dto = service.update(id, bookmarktype);
    LOGGER.info("BookmarkType with id {} updated successfully", id);
    return Response.ok(dto).build();
  }

  @DELETE
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Deletes an existing bookmarktype")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "204",
              description = "BookmarkType successfully deleted",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = BookmarkType.class))),
          @APIResponse(
              responseCode = "404",
              description = "No BookmarkType found for bookmarktypeId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response deleteBookmarkType(@PathParam("id") Long id){
    if(service.delete(id)){
      LOGGER.info("BookmarkType deleted with id {}", id);
      return Response.noContent().build();
    }
    ;
    return Response.status(Response.Status.BAD_REQUEST).build();
  }
}