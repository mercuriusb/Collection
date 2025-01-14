package org.mercuriusb.collectio.mapper;

import org.mapstruct.*;
import org.mercuriusb.collectio.dto.BookmarkTypeDto;
import org.mercuriusb.collectio.model.BookmarkType;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface BookmarkTypeMapper{

  BookmarkTypeDto toDto(BookmarkType entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  BookmarkType toEntity(BookmarkTypeDto domain);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  BookmarkType update(BookmarkTypeDto bookmarkTypeDto, @MappingTarget BookmarkType bookmarkType );

}