package org.mercuriusb.collectio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.soabase.recordbuilder.core.RecordBuilder;
import org.mercuriusb.collectio.utils.TreeNode;

import java.io.Serializable;
import java.util.*;

/**
 * DTO for {@link org.mercuriusb.collectio.model.Tag}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RecordBuilder
public record TagBrowseDto(
    Long id,
    UUID uuid,
    String value,
    @JsonIgnore String path,
    int count,
    Set<BookmarkViewDto> bookmarks,
    List<TagBrowseDto> children
) implements Serializable, TreeNode<TagBrowseDto>{

  public TagBrowseDto {
    if (bookmarks == null) {
      bookmarks = new LinkedHashSet<>();
    }
    if(children == null){
      children = new ArrayList<>();
    }
  }

  @Override
  public List<TagBrowseDto> getChildren(){
    return children;
  }

  @Override
  public void addChild(TagBrowseDto child){
    children.add(child);
  }

}


