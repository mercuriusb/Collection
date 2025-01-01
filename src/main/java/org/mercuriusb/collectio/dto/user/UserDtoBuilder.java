package org.mercuriusb.collectio.dto.user;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UserDtoBuilder{
  private OffsetDateTime created;
  private OffsetDateTime deleted;
  private String email;
  private Long id;
  private OffsetDateTime modified;
  private String name;
  private String password;
  private UUID uuid;

  public UserDto createUserDto(){
    return new UserDto(id, uuid, created, modified, deleted, name, password, email);
  }

  public UserDtoBuilder setCreated(OffsetDateTime created){
    this.created = created;
    return this;
  }

  public UserDtoBuilder setDeleted(OffsetDateTime deleted){
    this.deleted = deleted;
    return this;
  }

  public UserDtoBuilder setEmail(String email){
    this.email = email;
    return this;
  }

  public UserDtoBuilder setId(Long id){
    this.id = id;
    return this;
  }

  public UserDtoBuilder setModified(OffsetDateTime modified){
    this.modified = modified;
    return this;
  }

  public UserDtoBuilder setName(String name){
    this.name = name;
    return this;
  }

  public UserDtoBuilder setPassword(String password){
    this.password = password;
    return this;
  }

  public UserDtoBuilder setUuid(UUID uuid){
    this.uuid = uuid;
    return this;
  }
}