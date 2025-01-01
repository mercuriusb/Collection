package org.mercuriusb.collectio.dto.bookmark;

import org.mercuriusb.collectio.dto.tag.TagDto;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public class BookmarkViewDtoBuilder{
  private OffsetDateTime created;
  private String description;
  private Long id;
  private OffsetDateTime lastViewed;
  private OffsetDateTime modified;
  private String originalTitle;
  private String state;
  private String summary;
  private Set<TagDto> tags;
  private String title;
  private String typeValue;
  private String url;
  private UUID uuid;

  public BookmarkViewDto createBookmarkViewDto(){
    return new BookmarkViewDto(id, uuid, created, modified, lastViewed, url, originalTitle, summary, typeValue, title, description, state, tags);
  }

  public BookmarkViewDtoBuilder setCreated(OffsetDateTime created){
    this.created = created;
    return this;
  }

  public BookmarkViewDtoBuilder setDescription(String description){
    this.description = description;
    return this;
  }

  public BookmarkViewDtoBuilder setId(Long id){
    this.id = id;
    return this;
  }

  public BookmarkViewDtoBuilder setLastViewed(OffsetDateTime lastViewed){
    this.lastViewed = lastViewed;
    return this;
  }

  public BookmarkViewDtoBuilder setModified(OffsetDateTime modified){
    this.modified = modified;
    return this;
  }

  public BookmarkViewDtoBuilder setOriginalTitle(String originalTitle){
    this.originalTitle = originalTitle;
    return this;
  }

  public BookmarkViewDtoBuilder setState(String state){
    this.state = state;
    return this;
  }

  public BookmarkViewDtoBuilder setSummary(String summary){
    this.summary = summary;
    return this;
  }

  public BookmarkViewDtoBuilder setTags(Set<TagDto> tags){
    this.tags = tags;
    return this;
  }

  public BookmarkViewDtoBuilder setTitle(String title){
    this.title = title;
    return this;
  }

  public BookmarkViewDtoBuilder setTypeValue(String typeValue){
    this.typeValue = typeValue;
    return this;
  }

  public BookmarkViewDtoBuilder setUrl(String url){
    this.url = url;
    return this;
  }

  public BookmarkViewDtoBuilder setUuid(UUID uuid){
    this.uuid = uuid;
    return this;
  }
}