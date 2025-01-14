package org.mercuriusb.collectio.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Bookmark extends BaseEntity{
  @Column(name = "url", nullable = false, unique = true, columnDefinition = "text")
  private String url;
  @Column(name = "content", columnDefinition = "text")
  private String content;
  @Column(name = "mimetype", columnDefinition = "text")
  private String mimetype;
  @Column(name = "original_title", columnDefinition = "text")
  private String originalTitle;
  @Column(name = "plaintext", columnDefinition = "text")
  private String plaintext;
  @Column(name = "summary", columnDefinition = "text")
  private String summary;

  @Column(name = "last_viewed", nullable = true, updatable = true)
  private OffsetDateTime lastViewed;
  @Column(name = "last_checked", nullable = true, updatable = true)
  private OffsetDateTime lastChecked;


  @ManyToMany
  @JoinTable(name = "bookmark_tags",
      joinColumns = @JoinColumn(name = "bookmark_id"),
      inverseJoinColumns = @JoinColumn(name = "tags_id"))
  private Set<Tag> tags = new LinkedHashSet<>();

  @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<BookmarkUserMetaData> bookmarkUserMetaData = new LinkedHashSet<>();;

  @ManyToOne(optional = true)
  @JoinColumn(name = "type_id", nullable = true)
  private BookmarkType type;





  public Set<BookmarkUserMetaData> getBookmarkMetaData(){
    return bookmarkUserMetaData;
  }

  public void setBookmarkMetaData(Set<BookmarkUserMetaData> bookmarkUserMetaData){
    this.bookmarkUserMetaData = bookmarkUserMetaData;
  }

  public Bookmark addBookmarkMetaData(BookmarkUserMetaData bookmarkUserMetaData){
    this.bookmarkUserMetaData.add(bookmarkUserMetaData);
    if(bookmarkUserMetaData != null){
      bookmarkUserMetaData.setBookmark(this);
    }
    return this;
  }

  public Bookmark removeBookmarkMetaData(BookmarkUserMetaData bookmarkUserMetaData){
    this.bookmarkUserMetaData.remove(bookmarkUserMetaData);
    if(bookmarkUserMetaData != null){
      bookmarkUserMetaData.setBookmark(null);
    }
    return this;
  }

  public String getUrl(){
    return url;
  }

  public Bookmark setUrl(String url){
    this.url = url;
    return this;
  }

  public String getContent(){
    return content;
  }

  public Bookmark setContent(String content){
    this.content = content;
    return this;
  }

  public String getMimetype(){
    return mimetype;
  }

  public Bookmark setMimetype(String mimetype){
    this.mimetype = mimetype;
    return this;
  }

  public String getOriginalTitle(){
    return originalTitle;
  }

  public Bookmark setOriginalTitle(String originalTitle){
    this.originalTitle = originalTitle;
    return this;
  }

  public String getPlaintext(){
    return plaintext;
  }

  public Bookmark setPlaintext(String plaintext){
    this.plaintext = plaintext;
    return this;
  }

  public String getSummary(){
    return summary;
  }

  public Bookmark setSummary(String summary){
    this.summary = summary;
    return this;
  }

  public Set<Tag> getTags(){
    return tags;
  }

  public Bookmark setTags(Set<Tag> tags){
    this.tags = tags;
    return this;
  }

  public Bookmark addTag(Tag tag){
    this.tags.add(tag);
    if(tag.getBookmarks() != null){
      tag.getBookmarks().add(this);
    }
    return this;
  }

  public Bookmark removeTag(Tag tag){
    this.tags.remove(tag);
    if(tag.getBookmarks() != null){
      tag.getBookmarks().remove(this);
    }
    return this;
  }


  public BookmarkType getType(){
    return type;
  }

  public Bookmark setType(BookmarkType type){
    this.type = type;
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
    Bookmark bookmark = (Bookmark) o;
    return getId() != null && Objects.equals(getId(), bookmark.getId());
  }

  @Override
  public final int hashCode(){
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}