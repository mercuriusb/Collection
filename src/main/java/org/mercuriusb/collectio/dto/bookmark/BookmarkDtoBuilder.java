package org.mercuriusb.collectio.dto.bookmark;

import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDto;
import org.mercuriusb.collectio.dto.bookmarktype.BookmarkTypeDto;
import org.mercuriusb.collectio.dto.tag.TagDto;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class BookmarkDtoBuilder{
  private Set<BookmarkMetaDataDto> bookmarkMetaData;
  private String content;
  private OffsetDateTime created;
  private OffsetDateTime deleted;
  private Long id;
  private OffsetDateTime lastChecked;
  private OffsetDateTime lastViewed;
  private String mimetype;
  private OffsetDateTime modified;
  private String originalTitle;
  private String plaintext;
  private String summary;
  private Set<TagDto> tags;
  private BookmarkTypeDto type;
  private String url;
  private UUID uuid;

  public BookmarkDto createBookmarkDto(){
    return new BookmarkDto(id, uuid, created, modified, deleted, url, content, mimetype, originalTitle, plaintext, summary, tags, bookmarkMetaData, type, lastViewed, lastChecked);
  }

  public BookmarkDtoBuilder setBookmarkMetaData(Set<BookmarkMetaDataDto> bookmarkMetaData){
    this.bookmarkMetaData = bookmarkMetaData;
    return this;
  }

  /*
  public BookmarkDtoBuilder addBookmarkMetaData(BookmarkMetaDataDto bookmarkMetaData){
    if(this.bookmarkMetaData == null){
      this.bookmarkMetaData = new LinkedHashSet<>();
    }
    this.bookmarkMetaData.add(bookmarkMetaData);
    return this;
  }
   */


  public BookmarkDtoBuilder setContent(String content){
    this.content = content;
    return this;
  }

  public BookmarkDtoBuilder setCreated(OffsetDateTime created){
    this.created = created;
    return this;
  }

  public BookmarkDtoBuilder setDeleted(OffsetDateTime deleted){
    this.deleted = deleted;
    return this;
  }

  public BookmarkDtoBuilder setId(Long id){
    this.id = id;
    return this;
  }

  public BookmarkDtoBuilder setLastChecked(OffsetDateTime lastChecked){
    this.lastChecked = lastChecked;
    return this;
  }

  public BookmarkDtoBuilder setLastViewed(OffsetDateTime lastViewed){
    this.lastViewed = lastViewed;
    return this;
  }

  public BookmarkDtoBuilder setMimetype(String mimetype){
    this.mimetype = mimetype;
    return this;
  }

  public BookmarkDtoBuilder setModified(OffsetDateTime modified){
    this.modified = modified;
    return this;
  }

  public BookmarkDtoBuilder setOriginalTitle(String originalTitle){
    this.originalTitle = originalTitle;
    return this;
  }

  public BookmarkDtoBuilder setPlaintext(String plaintext){
    this.plaintext = plaintext;
    return this;
  }

  public BookmarkDtoBuilder setSummary(String summary){
    this.summary = summary;
    return this;
  }

  public BookmarkDtoBuilder setTags(Set<TagDto> tags){
    this.tags = tags;
    return this;
  }

  /*
  public BookmarkDtoBuilder addTag(TagDto tag){
    if(this.tags == null){
      this.tags = new LinkedHashSet<>();
    }
    this.tags.add(tag);
    return this;
  }
   */

  public BookmarkDtoBuilder setType(BookmarkTypeDto type){
    this.type = type;
    return this;
  }

  public BookmarkDtoBuilder setUrl(String url){
    this.url = url;
    return this;
  }

  public BookmarkDtoBuilder setUuid(UUID uuid){
    this.uuid = uuid;
    return this;
  }
}