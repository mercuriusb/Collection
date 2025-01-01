package org.mercuriusb.collectio.dto.user;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.User}
 */
public class UserDto implements Serializable{
  private final OffsetDateTime created;
  private final OffsetDateTime deleted;
  private final String email;
  private final Long id;
  private final OffsetDateTime modified;
  private final String name;
  private final String password;
  private final UUID uuid;

  public UserDto(Long id, UUID uuid, OffsetDateTime created, OffsetDateTime modified, OffsetDateTime deleted, String name, String password, String email){
    this.id = id;
    this.uuid = uuid;
    this.created = created;
    this.modified = modified;
    this.deleted = deleted;
    this.name = name;
    this.password = password;
    this.email = email;
  }

  @Override
  public boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(o == null || getClass() != o.getClass()){
      return false;
    }
    UserDto entity = (UserDto) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.uuid, entity.uuid) &&
        Objects.equals(this.created, entity.created) &&
        Objects.equals(this.modified, entity.modified) &&
        Objects.equals(this.deleted, entity.deleted) &&
        Objects.equals(this.name, entity.name) &&
        Objects.equals(this.password, entity.password) &&
        Objects.equals(this.email, entity.email);
  }

  public OffsetDateTime getCreated(){
    return created;
  }

  public OffsetDateTime getDeleted(){
    return deleted;
  }

  public String getEmail(){
    return email;
  }

  public Long getId(){
    return id;
  }

  public OffsetDateTime getModified(){
    return modified;
  }

  public String getName(){
    return name;
  }

  public String getPassword(){
    return password;
  }

  public UUID getUuid(){
    return uuid;
  }

  @Override
  public int hashCode(){
    return Objects.hash(id, uuid, created, modified, deleted, name, password, email);
  }

  @Override
  public String toString(){
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "uuid = " + uuid + ", " +
        "created = " + created + ", " +
        "modified = " + modified + ", " +
        "deleted = " + deleted + ", " +
        "name = " + name + ", " +
        "password = " + password + ", " +
        "email = " + email + ")";
  }
}