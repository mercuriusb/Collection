package org.mercuriusb.collectio.dto.bookmarkmetadata;

import org.mercuriusb.collectio.dto.user.UserDto;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDto;

public class BookmarkMetaDataDtoBuilder{
  private BookmarkDto bookmark;
  private String description;
  private Long id;
  private String state;
  private String title;
  private UserDto user;

  public BookmarkMetaDataDto createBookmarkMetaDataDto(){
    return new BookmarkMetaDataDto(id, title, description, state, bookmark, user);
  }

  public BookmarkMetaDataDtoBuilder setBookmark(BookmarkDto bookmark){
    this.bookmark = bookmark;
    return this;
  }

  public BookmarkMetaDataDtoBuilder setDescription(String description){
    this.description = description;
    return this;
  }

  public BookmarkMetaDataDtoBuilder setId(Long id){
    this.id = id;
    return this;
  }

  public BookmarkMetaDataDtoBuilder setState(String state){
    this.state = state;
    return this;
  }

  public BookmarkMetaDataDtoBuilder setTitle(String title){
    this.title = title;
    return this;
  }

  public BookmarkMetaDataDtoBuilder setUser(UserDto user){
    this.user = user;
    return this;
  }
}