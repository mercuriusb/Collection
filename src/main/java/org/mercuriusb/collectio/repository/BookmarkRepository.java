package org.mercuriusb.collectio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mercuriusb.collectio.model.Bookmark;

@ApplicationScoped
public class BookmarkRepository implements PanacheRepository<Bookmark>{
  public Bookmark findByURL(String url) {
    return find("url", url).firstResult();
  }

  //TODO create methods to retrieve bookmarks with user specific data

}