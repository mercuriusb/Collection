package org.mercuriusb.collectio.mapper;

import org.mapstruct.*;
import org.mercuriusb.collectio.dto.UserDto;
import org.mercuriusb.collectio.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UserMapper{

  UserDto toDto(User entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  User toEntity(UserDto domain);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User update(UserDto userDTO, @MappingTarget User user);

}