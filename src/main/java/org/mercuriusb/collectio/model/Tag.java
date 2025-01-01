package org.mercuriusb.collectio.model;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLLTreeType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;
import org.mercuriusb.collectio.utils.LtreeUtils;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag extends BaseEntity{
  @Column(name = "value", nullable = false, columnDefinition = "text")
  private String value;

  @Type(PostgreSQLLTreeType.class)
  @Column(name = "path", nullable = false, columnDefinition = "ltree")
  private String path;

  @ManyToMany(mappedBy = "tags")
  private Set<Bookmark> bookmarks;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public String getPath(){
    return path;
  }

  public String getValue(){
    return value;
  }

  public Tag setValue(String value){
    this.value = value;
    return this;
  }

  public Set<Bookmark> getBookmarks(){
    return bookmarks;
  }

  public Tag setBookmarks(Set<Bookmark> bookmarks){
    this.bookmarks = bookmarks;
    return this;
  }

  public Tag addBookmark(Bookmark bookmark){
    if(bookmarks == null){
      bookmarks = new LinkedHashSet<>();
    }
    bookmarks.add(bookmark);
    if(bookmark.getTags() == null){
      bookmark.getTags().add(this);
    }
    return this;
  }

  public Tag removeBookmark(Bookmark bookmark){
    if(bookmarks != null){
      bookmarks.remove(bookmark);
    }
    if(bookmark.getTags() == null){
      bookmark.getTags().remove(this);
    }
    return this;
  }

  public User getUser(){
    return user;
  }

  public Tag setUser(User user){
    this.user = user;
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
    Tag tag = (Tag) o;
    return getId() != null && Objects.equals(getId(), tag.getId());
  }

  @Override
  public final int hashCode(){
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }

  @Override
  public void prePersistMore(){
    path = LtreeUtils.escape(value);
  }

}