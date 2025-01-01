package org.mercuriusb.collectio.dto.bookmark;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.Bookmark}
 */
public class BookmarkExtractDto implements Serializable{
  private final Long id;
  private final String typeValue;
  private final String url;
  private final UUID uuid;

  public BookmarkExtractDto(Long id, UUID uuid, String url, String typeValue){
    this.id = id;
    this.uuid = uuid;
    this.url = url;
    this.typeValue = typeValue;
  }

  @Override
  public boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(o == null || getClass() != o.getClass()){
      return false;
    }
    BookmarkExtractDto entity = (BookmarkExtractDto) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.uuid, entity.uuid) &&
        Objects.equals(this.url, entity.url) &&
        Objects.equals(this.typeValue, entity.typeValue);
  }

  public Long getId(){
    return id;
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
    return Objects.hash(id, uuid, url, typeValue);
  }

  @Override
  public String toString(){
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "uuid = " + uuid + ", " +
        "url = " + url + ", " +
        "typeValue = " + typeValue + ")";
  }
}