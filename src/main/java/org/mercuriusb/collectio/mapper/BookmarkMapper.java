package org.mercuriusb.collectio.mapper;

import org.mapstruct.*;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDto;
import org.mercuriusb.collectio.dto.bookmark.BookmarkExtractDto;
import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDto;
import org.mercuriusb.collectio.dto.tag.TagDto;
import org.mercuriusb.collectio.model.Bookmark;
import org.mercuriusb.collectio.model.BookmarkMetaData;
import org.mercuriusb.collectio.model.Tag;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI,collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface BookmarkMapper{
  BookmarkDto toDto(Bookmark entity);


  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  Bookmark toEntity(BookmarkDto domain);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Bookmark update(BookmarkDto bookmarkDTO, @MappingTarget Bookmark bookmark);

  @Mapping(target = "bookmarks", ignore = true)
  TagDto toTagDto(Tag tag);

  @Mapping(target = "bookmark", ignore = true)
  BookmarkMetaDataDto toBookmarkMetaDataDto(BookmarkMetaData bookmarkMetaData);

  BookmarkExtractDto toBookmarkExtractDto(Bookmark entity);

}