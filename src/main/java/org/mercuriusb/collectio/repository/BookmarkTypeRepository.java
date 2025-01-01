package org.mercuriusb.collectio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import org.mercuriusb.collectio.model.BookmarkType;

@ApplicationScoped
public class BookmarkTypeRepository implements PanacheRepository<BookmarkType>{
   private static final Logger log = Logger.getLogger(BookmarkTypeRepository.class);

}