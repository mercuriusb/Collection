package org.mercuriusb.collectio.mapper;

import org.mapstruct.*;
import org.mercuriusb.collectio.dto.BookmarkDto;
import org.mercuriusb.collectio.dto.BookmarkMetaDataDto;
import org.mercuriusb.collectio.dto.TagDto;
import org.mercuriusb.collectio.model.Bookmark;
import org.mercuriusb.collectio.model.BookmarkUserMetaData;
import org.mercuriusb.collectio.model.Tag;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface TagMapper{

  TagDto toDto(Tag entity);

  @Mapping(target = "user", ignore = true)
  @Mapping(target = "bookmarks", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "modified", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  TagDto toDtoPlain(Tag entity);

  /*
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "modified", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(source = "eoBlockToAptomaMap", target = "eoBlockToAptomaMapList", qualifiedByName = "eoBlockToAptomaMapToList")
  TagBrowseDto toTagBrowseDtoPlain(Tag entity);
*/
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  Tag toEntity(TagDto domain);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Tag update(TagDto tagDTO, @MappingTarget Tag tag);

  @Mapping(target = "tags", ignore = true)
  BookmarkDto toDto(Bookmark bookmark);

  @Mapping(target = "bookmark", ignore = true)

  @Mapping(target = "user", ignore = true)
  BookmarkMetaDataDto toBookmarkMetaDataDto(BookmarkUserMetaData bookmarkUserMetaData);

}