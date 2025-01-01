package org.mercuriusb.collectio.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Cacheable
public class User extends BaseEntity{
  @Column(name = "name", nullable = false, unique = true, columnDefinition = "text")
  private String name;

  @Column(name = "password", nullable = true, columnDefinition = "text")
  private String password;

  @Column(name = "email", nullable = true, unique = true, columnDefinition = "text")
  private String email;

  public String getName(){
    return name;
  }

  public User setName(String name){
    this.name = name;
    return this;
  }

  public String getPassword(){
    return password;
  }

  public User setPassword(String password){
    this.password = password;
    return this;
  }

  public String getEmail(){
    return email;
  }

  public User setEmail(String email){
    this.email = email;
    return this;
  }

  @Override
  public final boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(o == null){
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if(thisEffectiveClass != oEffectiveClass){
      return false;
    }
    User user = (User) o;
    return getId() != null && Objects.equals(getId(), user.getId());
  }

  @Override
  public final int hashCode(){
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}