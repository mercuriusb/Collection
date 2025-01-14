package org.mercuriusb.collectio.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
public class BookmarkUserMetaData{
  @ManyToOne
  @JoinColumn(name = "bookmark_id")
  private Bookmark bookmark;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "title", nullable = true, columnDefinition = "text")
  private String title;

  @Column(name = "description", nullable = true, columnDefinition = "text")
  private String description;

  @Column(name = "state", nullable = true, columnDefinition = "text")
  private String state;


  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Bookmark getBookmark(){
    return bookmark;
  }

  public void setBookmark(Bookmark bookmark){
    this.bookmark = bookmark;
  }

  public Long getId(){
    return id;
  }

  public BookmarkUserMetaData setId(Long id){
    this.id = id;
    return this;
  }

  public String getTitle(){
    return title;
  }

  public BookmarkUserMetaData setTitle(String title){
    this.title = title;
    return this;
  }

  public String getDescription(){
    return description;
  }

  public BookmarkUserMetaData setDescription(String description){
    this.description = description;
    return this;
  }

  public String getState(){
    return state;
  }

  public BookmarkUserMetaData setState(String state){
    this.state = state;
    return this;
  }

  public User getUser(){
    return user;
  }

  public BookmarkUserMetaData setUser(User user){
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
    BookmarkUserMetaData that = (BookmarkUserMetaData) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode(){
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}