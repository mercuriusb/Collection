
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

import org.mercuriusb.collectio.dto.user.UserDto;
import org.mercuriusb.collectio.model.User;
import org.mercuriusb.collectio.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;


@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource{

  private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
  private final UserService service;

  // constructor injection
  public UserResource(UserService userService){
    this.service = userService;
  }

  @GET
  @Authenticated
  @Operation(summary = "Returns all existing users")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get All Users",
              content = @Content(mediaType = "application/json",
              schema = @Schema(type = SchemaType.ARRAY, implementation = User.class))),
          @APIResponse(
              responseCode = "404",
              description = "No users found",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getAllUsers(){
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Returns an user given the user Id")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "Get User by userId",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = SchemaType.OBJECT, implementation = User.class))),
          @APIResponse(
              responseCode = "404",
              description = "No User found for the userId provided",
              content = @Content(mediaType = "application/json"))
      }
  )
  public Response getUserById(@PathParam("id") Long id){
    UserDto dto = service.getById(id);
    if(dto != null){
      LOGGER.info("Found user {}", dto);
      return Response.ok(dto).build();
    }else{
      LOGGER.debug("No user found with id {}", id);
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }
/*
  @POST
  @Authenticated
  @Operation(summary = "Creates a new user")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "201",
              description = "User created successfully",
              content = @Content(mediaType = "application/json",
              schema = @Schema(type = SchemaType.OBJECT, implementation = User.class))),
          @APIResponse(
              responseCode = "400",
              description = "User already exists with userId",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response createUser(@RequestBody(required = true) @Valid UserDto user){
    service.create(user);
    URI userUrl = URI.create("/api/v1/users/" + user.getId());
    LOGGER.info("New user added at URL {}", userUrl);
    return Response.created(userUrl).build();
  }

  @PUT
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Updates an existing user")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "200",
              description = "User successfully updated",
              content = @Content(mediaType = "application/json",
              schema = @Schema(type = SchemaType.OBJECT, implementation = User.class))),
          @APIResponse(
              responseCode = "404",
              description = "No User found for userId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response updateUser(@PathParam("id") Long id, @RequestBody @Valid UserDto user){
    UserDto dto = service.getById(id);
    if(dto == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    dto = service.update(id,user);
    LOGGER.info("User with id {} updated successfully", id);
    return Response.ok(dto).build();
  }

  @DELETE
  @Authenticated
  @Path("/{id}")
  @Operation(summary = "Deletes an existing user")
  @APIResponses(
      value = {
          @APIResponse(
              responseCode = "204",
              description = "User successfully deleted",
              content = @Content(mediaType = "application/json",
              schema = @Schema(type = SchemaType.OBJECT, implementation = User.class))),
          @APIResponse(
              responseCode = "404",
              description = "No User found for userId provided",
              content = @Content(mediaType = "application/json")),
      }
  )
  public Response deleteUser(@PathParam("id") Long id){
    if(service.delete(id)){
      LOGGER.info("User deleted with id {}", id);
      return Response.noContent().build();
    };
    return Response.status(Response.Status.BAD_REQUEST).build();
  }
  */
}