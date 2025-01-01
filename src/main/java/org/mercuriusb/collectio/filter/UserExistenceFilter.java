package org.mercuriusb.collectio.filter;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.mercuriusb.collectio.dto.user.UserDto;
import org.mercuriusb.collectio.service.UserService;
import org.mercuriusb.collectio.model.User;

@Provider
public class UserExistenceFilter implements ContainerRequestFilter {

  @Inject
  SecurityIdentity securityIdentity; // Retrieve authenticated user's info
  @Inject
  UserService userService;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    // Get the username and Keycloak ID from securityIdentity
    String username = securityIdentity.getPrincipal().getName();

    // Ensure user exists and retrieve their database record
    UserDto user = userService.ensureUserExistsAndGet(username);
    requestContext.setProperty("userId", user.getId());

  }
}