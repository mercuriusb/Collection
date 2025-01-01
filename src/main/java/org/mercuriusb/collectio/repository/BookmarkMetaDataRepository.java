package org.mercuriusb.collectio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mercuriusb.collectio.model.BookmarkMetaData;

@ApplicationScoped
public class BookmarkMetaDataRepository implements PanacheRepository<BookmarkMetaData>{
  public long deleteByBookmarkIDAndUserId(long bookmarkID, long userID) {
    return delete("bookmark.id = ?1 and user.id = ?2", bookmarkID, userID);
  }

}