
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

import org.mercuriusb.collectio.dto.bookmark.BookmarkDto;
import org.mercuriusb.collectio.model.Bookmark;
import org.mercuriusb.collectio.service.BookmarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;


@Path("/api/v1/bookmarks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookmarkResource{

  private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkResource.class);
  private final BookmarkService service;

  // constructor injection
  public BookmarkResource(BookmarkService bookmarkService){
    this.service = bookmarkService;
  }

  @GET
  @Authenticated
  @Operation(summary = "Returns all existing bookmarks")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get All Bookmarks",
              content = @Content(mediaType = "application/json",
              schema = @Schema(type = SchemaType.ARRAY, implementation = Bookmark.class))),
          @APIResponse(
              responseCode = "404",
              description = "No bookmarks found",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getAllBookmarks(@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    return Response.ok(service.getAll(userId)).build();
  }

  @GET
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Returns an bookmark given the bookmark Id")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get Bookmark by bookmarkId",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = Bookmark.class))),
          @APIResponse(
              responseCode = "404",
              description = "No Bookmark found for the bookmarkId provided",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getBookmarkById(@PathParam("id") Long id,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    BookmarkDto dto = service.getById(id,userId);
    if(dto != null){
      LOGGER.info("Found bookmark {}", dto);
      return Response.ok(dto).build();
    }else{
      LOGGER.debug("No bookmark found with id {}", id);
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @POST
  @Authenticated
  @Operation(summary = "Creates a new bookmark")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "201",
              description = "Bookmark created successfully",
              content = @Content(mediaType = "application/json",
              schema = @Schema(type = SchemaType.OBJECT, implementation = Bookmark.class))),
          @APIResponse(
              responseCode = "400",
              description = "Bookmark already exists with bookmarkId",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response createBookmark(@RequestBody(required = true) @Valid BookmarkDto bookmark,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    URI bookmarkUrl = URI.create("/api/v1/bookmarks/" + bookmark.getId());
    LOGGER.info("New bookmark added at URL {}", bookmarkUrl);
    return Response.created(bookmarkUrl).build();
  }

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Path("/import")
  @Authenticated
  public Response importBookmarks(String html,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    service.importBookmarks(html,userId);
    LOGGER.info("imported bookmark for user {}", userId);
    return Response.ok().build();
  }


  @PUT
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Updates an existing bookmark")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Bookmark successfully updated",
              content = @Content(mediaType = "application/json",
              schema = @Schema(type = SchemaType.OBJECT, implementation = Bookmark.class))),
          @APIResponse(
              responseCode = "404",
              description = "No Bookmark found for bookmarkId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response updateBookmark(@PathParam("id") Long id, @RequestBody @Valid BookmarkDto bookmark,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    BookmarkDto dto = service.getById(id,userId);
    if(dto == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    dto = service.update(id,bookmark);
    LOGGER.info("Bookmark with id {} updated successfully", id);
    return Response.ok(dto).build();
  }

  @DELETE
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Deletes an existing bookmark")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "204",
              description = "Bookmark successfully deleted",
              content = @Content(mediaType = "application/json",
              schema = @Schema(type = SchemaType.OBJECT, implementation = Bookmark.class))),
          @APIResponse(
              responseCode = "404",
              description = "No Bookmark found for bookmarkId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response deleteBookmark(@PathParam("id") Long id,@Context ContainerRequestContext requestContext){
    Long userId = (Long)requestContext.getProperty("userId");
    if(service.delete(id,userId)){
      LOGGER.info("Bookmark deleted with id {}", id);
      return Response.noContent().build();
    }
    return Response.status(Response.Status.BAD_REQUEST).build();
  }
}