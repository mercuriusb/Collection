package org.mercuriusb.collectio.mapper;

import org.mapstruct.*;
import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDto;
import org.mercuriusb.collectio.model.BookmarkMetaData;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface BookmarkMetaDataMapper{

  @Mapping(target = "bookmark", ignore = true)
  @Mapping(target = "id", ignore = true)
  BookmarkMetaDataDto toDto(BookmarkMetaData entity);

  BookmarkMetaData toEntity(BookmarkMetaDataDto domain);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  BookmarkMetaData update(BookmarkMetaDataDto bookmarkMetaDataDto, @MappingTarget BookmarkMetaData bookmarkMetaData);

}