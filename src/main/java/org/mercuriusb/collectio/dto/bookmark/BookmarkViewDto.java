package org.mercuriusb.collectio.dto.bookmark;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.mercuriusb.collectio.dto.tag.TagDto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.Bookmark}
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookmarkViewDto implements Serializable{
  private final Long id;
  private final UUID uuid;
  private final OffsetDateTime created;
  private final OffsetDateTime modified;
  private final OffsetDateTime lastViewed;
  private final String url;
  private final String originalTitle;
  private final String summary;
  private final String typeValue;
  private String title;
  private String description;
  private String state;
  private final Set<TagDto> tags;

  public BookmarkViewDto(Long id,
                         UUID uuid,
                         OffsetDateTime created,
                         OffsetDateTime modified,
                         OffsetDateTime lastViewed,
                         String url,
                         String originalTitle,
                         String summary,
                         String typeValue,
                         String title,
                         String description,
                         String state,
                         Set<TagDto> tags
                         ){
    this.id = id;
    this.uuid = uuid;
    this.created = created;
    this.modified = modified;
    this.lastViewed = lastViewed;
    this.url = url;
    this.originalTitle = originalTitle;
    this.summary = summary;
    this.typeValue = typeValue;
    this.title = title;
    this.description = description;
    this.state = state;
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o){
    if(o == null || getClass() != o.getClass()){
      return false;
    }
    BookmarkViewDto that = (BookmarkViewDto) o;
    return Objects.equals(created, that.created) && Objects.equals(id, that.id) && Objects.equals(lastViewed, that.lastViewed) && Objects.equals(modified, that.modified) && Objects.equals(originalTitle, that.originalTitle) && Objects.equals(summary, that.summary) && Objects.equals(tags, that.tags) && Objects.equals(typeValue, that.typeValue) && Objects.equals(url, that.url) && Objects.equals(uuid, that.uuid) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(state, that.state);
  }

  public OffsetDateTime getCreated(){
    return created;
  }

  public String getDescription(){
    return description;
  }

  public BookmarkViewDto setDescription(String description){
    this.description = description;
    return this;
  }

  public Long getId(){
    return id;
  }

  public OffsetDateTime getLastViewed(){
    return lastViewed;
  }

  public OffsetDateTime getModified(){
    return modified;
  }

  public String getOriginalTitle(){
    return originalTitle;
  }

  public String getState(){
    return state;
  }

  public BookmarkViewDto setState(String state){
    this.state = state;
    return this;
  }

  public String getSummary(){
    return summary;
  }

  public Set<TagDto> getTags(){
    return tags;
  }

  public String getTitle(){
    return title;
  }

  public BookmarkViewDto setTitle(String title){
    this.title = title;
    return this;
  }

  public String getTypeValue(){
    return typeValue;
  }

  public String getUrl(){
    return url;
  }

  public UUID getUuid(){
    return uuid;
  }

  @Override
  public int hashCode(){
    return Objects.hash(created, id, lastViewed, modified, originalTitle, summary, tags, typeValue, url, uuid, title, description, state);
  }
}