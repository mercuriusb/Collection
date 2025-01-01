package org.mercuriusb.collectio.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "uuid", nullable = false, unique = true)
  private UUID uuid;

  @Column(name = "created", nullable = false, updatable = false)
  private OffsetDateTime created;

  @Column(name = "modified", nullable = false, updatable = false)
  private OffsetDateTime modified;

  @Column(name = "deleted")
  private OffsetDateTime deleted;

  @PrePersist
  public void prePersist(){
    uuid = UUID.randomUUID();
    created = OffsetDateTime.now();
    modified = OffsetDateTime.now();
    prePersistMore();
  }

  public void prePersistMore(){
    return;
  };

  @PreUpdate
  public void preUpdate(){
    modified = OffsetDateTime.now();
  }

  public Long getId(){
    return id;
  }

  public BaseEntity setId(Long id){
    this.id = id;
    return this;
  }

  public UUID getUuid(){
    return uuid;
  }

  public BaseEntity setUuid(UUID uuid){
    this.uuid = uuid;
    return this;
  }

  public OffsetDateTime getCreated(){
    return created;
  }

  public BaseEntity setCreated(OffsetDateTime created){
    this.created = created;
    return this;
  }

  public OffsetDateTime getModified(){
    return modified;
  }

  public BaseEntity setModified(OffsetDateTime modified){
    this.modified = modified;
    return this;
  }

  public OffsetDateTime getDeleted(){
    return deleted;
  }

  public BaseEntity setDeleted(OffsetDateTime deleted){
    this.deleted = deleted;
    return this;
  }
}