package org.mercuriusb.collectio.dto.bookmarktype;

import java.time.OffsetDateTime;
import java.util.UUID;

public class BookmarkTypeDtoBuilder{
  private OffsetDateTime created;
  private OffsetDateTime deleted;
  private Long id;
  private OffsetDateTime modified;
  private UUID uuid;
  private String value;

  public BookmarkTypeDto createBookmarkTypeDto(){
    return new BookmarkTypeDto(id, uuid, created, modified, deleted, value);
  }

  public BookmarkTypeDtoBuilder setCreated(OffsetDateTime created){
    this.created = created;
    return this;
  }

  public BookmarkTypeDtoBuilder setDeleted(OffsetDateTime deleted){
    this.deleted = deleted;
    return this;
  }

  public BookmarkTypeDtoBuilder setId(Long id){
    this.id = id;
    return this;
  }

  public BookmarkTypeDtoBuilder setModified(OffsetDateTime modified){
    this.modified = modified;
    return this;
  }

  public BookmarkTypeDtoBuilder setUuid(UUID uuid){
    this.uuid = uuid;
    return this;
  }

  public BookmarkTypeDtoBuilder setValue(String value){
    this.value = value;
    return this;
  }
}