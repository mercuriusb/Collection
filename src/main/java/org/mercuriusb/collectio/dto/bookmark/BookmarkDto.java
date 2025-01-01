package org.mercuriusb.collectio.dto.bookmark;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDto;
import org.mercuriusb.collectio.dto.bookmarktype.BookmarkTypeDto;
import org.mercuriusb.collectio.dto.tag.TagDto;
import org.mercuriusb.collectio.model.Bookmark;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * DTO for {@link Bookmark}
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookmarkDto implements Serializable{
  private final Set<BookmarkMetaDataDto> bookmarkMetaData;
  private final String content;
  private final OffsetDateTime created;
  private final OffsetDateTime deleted;
  private final Long id;
  private final OffsetDateTime lastChecked;
  private final OffsetDateTime lastViewed;
  private final String mimetype;
  private final OffsetDateTime modified;
  private final String originalTitle;
  private final String plaintext;
  private final String summary;
  private final Set<TagDto> tags;
  private final BookmarkTypeDto type;
  private final String url;
  private final UUID uuid;

  public BookmarkDto(Long id, UUID uuid, OffsetDateTime created, OffsetDateTime modified, OffsetDateTime deleted, String url, String content, String mimetype, String originalTitle, String plaintext, String summary, Set<TagDto> tags, Set<BookmarkMetaDataDto> bookmarkMetaData, BookmarkTypeDto type,
                     OffsetDateTime lastViewed,
                     OffsetDateTime lastChecked){
    this.id = id;
    this.uuid = uuid;
    this.created = created;
    this.modified = modified;
    this.deleted = deleted;
    this.url = url;
    this.content = content;
    this.mimetype = mimetype;
    this.originalTitle = originalTitle;
    this.plaintext = plaintext;
    this.summary = summary;
    this.tags = tags != null ? tags : new LinkedHashSet<>();
    this.bookmarkMetaData = bookmarkMetaData != null ? bookmarkMetaData : new LinkedHashSet<>();
    this.type = type;
    this.lastViewed = lastViewed;
    this.lastChecked = lastChecked;
  }

  @Override
  public boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(o == null || getClass() != o.getClass()){
      return false;
    }
    BookmarkDto entity = (BookmarkDto) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.uuid, entity.uuid) &&
        Objects.equals(this.created, entity.created) &&
        Objects.equals(this.modified, entity.modified) &&
        Objects.equals(this.deleted, entity.deleted) &&
        Objects.equals(this.url, entity.url) &&
        Objects.equals(this.content, entity.content) &&
        Objects.equals(this.mimetype, entity.mimetype) &&
        Objects.equals(this.originalTitle, entity.originalTitle) &&
        Objects.equals(this.plaintext, entity.plaintext) &&
        Objects.equals(this.summary, entity.summary) &&
        Objects.equals(this.tags, entity.tags) &&
        Objects.equals(this.bookmarkMetaData, entity.bookmarkMetaData) &&
        Objects.equals(this.type, entity.type);
  }

  public Set<BookmarkMetaDataDto> getBookmarkMetaData(){
    return bookmarkMetaData;
  }

  public void addBookmarkMetaData(BookmarkMetaDataDto bookmarkMetaData){
    this.bookmarkMetaData.add(bookmarkMetaData);
  }

  public void removeBookmarkMetaData(BookmarkMetaDataDto bookmarkMetaData){
    this.bookmarkMetaData.remove(bookmarkMetaData);
  }

  public String getContent(){
    return content;
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

  public String getMimetype(){
    return mimetype;
  }

  public OffsetDateTime getModified(){
    return modified;
  }

  public String getOriginalTitle(){
    return originalTitle;
  }

  public String getPlaintext(){
    return plaintext;
  }

  public String getSummary(){
    return summary;
  }

  public Set<TagDto> getTags(){
    return tags;
  }

  public void addTag(TagDto tag){
    tags.add(tag);
  }

  public void removeTag(TagDto tag){
    tags.remove(tag);
  }

  public BookmarkTypeDto getType(){
    return type;
  }

  public String getUrl(){
    return url;
  }

  public UUID getUuid(){
    return uuid;
  }

  @Override
  public int hashCode(){
    return Objects.hash(id, uuid, created, modified, deleted, url, content, mimetype, originalTitle, plaintext, summary, tags, bookmarkMetaData, type);
  }

  @Override
  public String toString(){
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "uuid = " + uuid + ", " +
        "created = " + created + ", " +
        "modified = " + modified + ", " +
        "deleted = " + deleted + ", " +
        "url = " + url + ", " +
        "content = " + content + ", " +
        "mimetype = " + mimetype + ", " +
        "originalTitle = " + originalTitle + ", " +
        "plaintext = " + plaintext + ", " +
        "summary = " + summary + ", " +
        "tags = " + tags + ", " +
        "bookmarkMetaData = " + bookmarkMetaData + ", " +
        "type = " + type + ")";
  }

  public OffsetDateTime getLastViewed(){
    return lastViewed;
  }

  public OffsetDateTime getLastChecked(){
    return lastChecked;
  }
}