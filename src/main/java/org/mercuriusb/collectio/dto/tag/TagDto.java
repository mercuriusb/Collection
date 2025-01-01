package org.mercuriusb.collectio.dto.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.mercuriusb.collectio.dto.user.UserDto;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.Tag}
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TagDto implements Serializable{
  private final Set<BookmarkDto> bookmarks;
  private final OffsetDateTime created;
  private final OffsetDateTime deleted;
  private final Long id;
  private final OffsetDateTime modified;
  private final UserDto user;
  private final UUID uuid;
  private final String value;

  public TagDto(Long id, UUID uuid, OffsetDateTime created, OffsetDateTime modified, OffsetDateTime deleted, String value, Set<BookmarkDto> bookmarks, UserDto user){
    this.id = id;
    this.uuid = uuid;
    this.created = created;
    this.modified = modified;
    this.deleted = deleted;
    this.value = value;
    this.bookmarks = bookmarks != null ? bookmarks : new LinkedHashSet<>();
    this.user = user;
  }

  @Override
  public boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(o == null || getClass() != o.getClass()){
      return false;
    }
    TagDto entity = (TagDto) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.uuid, entity.uuid) &&
        Objects.equals(this.created, entity.created) &&
        Objects.equals(this.modified, entity.modified) &&
        Objects.equals(this.deleted, entity.deleted) &&
        Objects.equals(this.value, entity.value) &&
        Objects.equals(this.bookmarks, entity.bookmarks) &&
        Objects.equals(this.user, entity.user);
  }

  public Set<BookmarkDto> getBookmarks(){
    return bookmarks;
  }

  public void addBookmark(BookmarkDto bookmark){
    bookmarks.add(bookmark);
  }

  public void removeBookmark(BookmarkDto bookmark){
    bookmarks.remove(bookmark);
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

  public UserDto getUser(){
    return user;
  }

  public UUID getUuid(){
    return uuid;
  }

  public String getValue(){
    return value;
  }

  @Override
  public int hashCode(){
    return Objects.hash(id, uuid, created, modified, deleted, value, bookmarks, user);
  }

  @Override
  public String toString(){
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "uuid = " + uuid + ", " +
        "created = " + created + ", " +
        "modified = " + modified + ", " +
        "deleted = " + deleted + ", " +
        "value = " + value + ", " +
        "bookmarks = " + bookmarks + ", " +
        "user = " + user + ")";
  }
}