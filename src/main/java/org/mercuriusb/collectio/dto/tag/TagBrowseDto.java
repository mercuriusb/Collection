package org.mercuriusb.collectio.dto.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.mercuriusb.collectio.dto.bookmark.BookmarkViewDto;
import org.mercuriusb.collectio.utils.LtreeUtils;
import org.mercuriusb.collectio.utils.TreeNode;

import java.io.Serializable;
import java.util.*;

/**
 * DTO for {@link org.mercuriusb.collectio.model.Tag}
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TagBrowseDto implements Serializable, TreeNode<TagBrowseDto>{
  private final Set<BookmarkViewDto> bookmarks;
  private final Long id;
  @JsonIgnore
  private final String path;
  private final UUID uuid;
  private final String value;
  private final int count;

  private final List<TagBrowseDto> children = new ArrayList<>();

  public TagBrowseDto(Long id, UUID uuid, String value, String path,int count,Set<BookmarkViewDto> bookmarks){
    this.id = id;
    this.uuid = uuid;
    this.value = value;
    this.path = path;
    this.count = count;
    this.bookmarks = bookmarks;
  }

  @Override
  public boolean equals(Object o){
    if(this == o)
      return true;
    if(o == null || getClass() != o.getClass())
      return false;
    TagBrowseDto entity = (TagBrowseDto) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.uuid, entity.uuid) &&
        Objects.equals(this.value, entity.value) &&
        Objects.equals(this.bookmarks, entity.bookmarks);
  }

  public Set<BookmarkViewDto> getBookmarks(){
    return bookmarks;
  }

  public List<TagBrowseDto> getChildren(){
    return children;
  }

  public void addChild(TagBrowseDto child){
    children.add(child);
  }

  public int getCount(){
    return count;
  }

  public Long getId(){
    return id;
  }

  public UUID getUuid(){
    return uuid;
  }

  public String getValue(){
    return value;
  }

  @Override
  public int hashCode(){
    return Objects.hash(id, uuid, value, bookmarks);
  }

  @Override
  public String toString(){
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "uuid = " + uuid + ", " +
        "value = " + value + ", " +
        "bookmarks = " + bookmarks  + ")";
  }

  public String getPath(){
    return path;
  }
}