package org.mercuriusb.collectio.dto.tag;

import org.mercuriusb.collectio.dto.user.UserDto;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDto;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class TagDtoBuilder{
  private Set<BookmarkDto> bookmarks;
  private OffsetDateTime created;
  private OffsetDateTime deleted;
  private Long id;
  private OffsetDateTime modified;
  private UserDto user;
  private UUID uuid;
  private String value;

  public TagDto createTagDto(){
    return new TagDto(id, uuid, created, modified, deleted, value, bookmarks, user);
  }

  public TagDtoBuilder setBookmarks(Set<BookmarkDto> bookmarks){
    this.bookmarks = bookmarks;
    return this;
  }

  public TagDtoBuilder addBookmark(BookmarkDto bookmark){
    if(this.bookmarks == null){
      this.bookmarks = new LinkedHashSet<>();
    }
    this.bookmarks.add(bookmark);
    return this;
  }

  public TagDtoBuilder setCreated(OffsetDateTime created){
    this.created = created;
    return this;
  }

  public TagDtoBuilder setDeleted(OffsetDateTime deleted){
    this.deleted = deleted;
    return this;
  }

  public TagDtoBuilder setId(Long id){
    this.id = id;
    return this;
  }

  public TagDtoBuilder setModified(OffsetDateTime modified){
    this.modified = modified;
    return this;
  }

  public TagDtoBuilder setUser(UserDto user){
    this.user = user;
    return this;
  }

  public TagDtoBuilder setUuid(UUID uuid){
    this.uuid = uuid;
    return this;
  }

  public TagDtoBuilder setValue(String value){
    this.value = value;
    return this;
  }
}