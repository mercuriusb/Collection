package org.mercuriusb.collectio;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.mercuriusb.collectio.utils.BookmarkImporter;

@Path("/hello")
public class GreetingResource{

  @Inject
  BookmarkImporter importer;
  @Inject
  SecurityContext securityContext;

  @Inject
  SecurityIdentity identity;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello(){
    importer.importSafariBookmarks("/Users/hermesb/Safari-Lesezeichen.html");
    return "Hello from Quarkus REST";
  }

  @GET
  @Path("/test")
  @Produces(MediaType.TEXT_PLAIN)
  @Authenticated
  public String test(@Context ContainerRequestContext requestContext){
    /*
    String userId = securityContext.getUserPrincipal() != null
        ? securityContext.getUserPrincipal().getName() : null;
    identity.getRoles().forEach(System.out::println);
     */

    Long userId = (Long)requestContext.getProperty("userId");
    try{
      identity.getRoles().forEach(System.out::println);
    }catch(Exception e){
      e.printStackTrace();
    }
    return "name: " + userId;
  }
}
