package org.mercuriusb.collectio.dto.bookmarktype;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.BookmarkType}
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookmarkTypeDto implements Serializable{
  private final OffsetDateTime created;
  private final OffsetDateTime deleted;
  private final Long id;
  private final OffsetDateTime modified;
  private final UUID uuid;
  private final String value;

  public BookmarkTypeDto(Long id, UUID uuid, OffsetDateTime created, OffsetDateTime modified, OffsetDateTime deleted, String value){
    this.id = id;
    this.uuid = uuid;
    this.created = created;
    this.modified = modified;
    this.deleted = deleted;
    this.value = value;
  }

  @Override
  public boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(o == null || getClass() != o.getClass()){
      return false;
    }
    BookmarkTypeDto entity = (BookmarkTypeDto) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.uuid, entity.uuid) &&
        Objects.equals(this.created, entity.created) &&
        Objects.equals(this.modified, entity.modified) &&
        Objects.equals(this.deleted, entity.deleted) &&
        Objects.equals(this.value, entity.value);
  }

  public OffsetDateTime getCreated(){
    return created;
  }

  public OffsetDateTime getDeleted(){
    return deleted;
  }

  public Long getId(){
    return id;
  }

  public OffsetDateTime getModified(){
    return modified;
  }

  public UUID getUuid(){
    return uuid;
  }

  public String getValue(){
    return value;
  }

  @Override
  public int hashCode(){
    return Objects.hash(id, uuid, created, modified, deleted, value);
  }

  @Override
  public String toString(){
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "uuid = " + uuid + ", " +
        "created = " + created + ", " +
        "modified = " + modified + ", " +
        "deleted = " + deleted + ", " +
        "value = " + value + ")";
  }
}