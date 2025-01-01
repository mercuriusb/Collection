package org.mercuriusb.collectio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import org.mercuriusb.collectio.model.Tag;
import org.mercuriusb.collectio.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User>{
  private static final Logger log = Logger.getLogger(UserRepository.class);

  public User findByName(String username) {
    return find("name like ?1 ", username).firstResult();
  }


}