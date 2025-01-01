package org.mercuriusb.collectio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
public class BookmarkType extends BaseEntity{
  @Column(name = "value", nullable = false, columnDefinition = "text")
  private String value;

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
    BookmarkType that = (BookmarkType) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode(){
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }

  public String getValue(){
    return value;
  }

  public BookmarkType setValue(String value){
    this.value = value;
    return this;
  }

}