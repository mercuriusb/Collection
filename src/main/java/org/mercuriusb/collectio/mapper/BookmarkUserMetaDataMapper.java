package org.mercuriusb.collectio.mapper;

import org.mapstruct.*;
import org.mercuriusb.collectio.dto.BookmarkMetaDataDto;
import org.mercuriusb.collectio.model.BookmarkUserMetaData;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface BookmarkUserMetaDataMapper{

  @Mapping(target = "bookmark", ignore = true)
  @Mapping(target = "id", ignore = true)
  BookmarkMetaDataDto toDto(BookmarkUserMetaData entity);

  BookmarkUserMetaData toEntity(BookmarkMetaDataDto domain);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  BookmarkUserMetaData update(BookmarkMetaDataDto bookmarkMetaDataDto, @MappingTarget BookmarkUserMetaData bookmarkUserMetaData);

}