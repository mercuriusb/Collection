package org.mercuriusb.collectio.dto.bookmarkmetadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.mercuriusb.collectio.dto.user.UserDto;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link org.mercuriusb.collectio.model.BookmarkMetaData}
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookmarkMetaDataDto implements Serializable{
  private final BookmarkDto bookmark;
  private final String description;
  private final Long id;
  private final String state;
  private final String title;
  private final UserDto user;

  public BookmarkMetaDataDto(Long id, String title, String description, String state, BookmarkDto bookmark,
                             UserDto user){
    this.id = id;
    this.title = title;
    this.description = description;
    this.state = state;
    this.bookmark = bookmark;
    this.user = user;
  }

  @Override
  public boolean equals(Object o){
    if(this == o)
      return true;
    if(o == null || getClass() != o.getClass())
      return false;
    BookmarkMetaDataDto entity = (BookmarkMetaDataDto) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.title, entity.title) &&
        Objects.equals(this.description, entity.description) &&
        Objects.equals(this.state, entity.state) &&
        Objects.equals(this.bookmark, entity.bookmark);
  }

  public BookmarkDto getBookmark(){
    return bookmark;
  }

  public String getDescription(){
    return description;
  }

  public Long getId(){
    return id;
  }

  public String getState(){
    return state;
  }

  public String getTitle(){
    return title;
  }

  @Override
  public int hashCode(){
    return Objects.hash(id, title, description, state, bookmark);
  }

  @Override
  public String toString(){
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "title = " + title + ", " +
        "description = " + description + ", " +
        "state = " + state + ", " +
        "bookmark = " + bookmark + ")";
  }

  public UserDto getUser(){
    return user;
  }
}