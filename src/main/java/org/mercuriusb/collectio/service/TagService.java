
package org.mercuriusb.collectio.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import org.mercuriusb.collectio.dto.TagBrowseDto;
import org.mercuriusb.collectio.dto.TagDto;
import org.mercuriusb.collectio.mapper.TagMapper;
import org.mercuriusb.collectio.model.Tag;
import org.mercuriusb.collectio.repository.TagRepository;
import org.mercuriusb.collectio.repository.UserRepository;
import org.mercuriusb.collectio.utils.LtreeUtils;
import org.mercuriusb.collectio.utils.TreeUtils;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TagService{

  @PersistenceContext
  private EntityManager entityManager;  // Inject EntityManager

  @Inject
  TagRepository repository;
  @Inject
  UserRepository userRepository;
  @Inject
  TagMapper mapper;

  public TagDto getById(Long id,long userId) throws WebApplicationException{
    Tag tag = repository.findById(id);
    if(tag == null){
      throw new NotFoundException(String.format("Tag with id %s for user %s not found",id,userId));
    }
    if(userId != tag.getUser().getId()){
      throw new NotAllowedException(String.format("User with id[%s] is not allowed to access tag with id %s", userId,id));
    }
    return mapper.toDto(tag);
  }

  public List<TagDto> getAll(long userId){
    return repository.listAllByUserID(userId).stream()
//                     .peek(entityManager::detach)
                     .map(mapper::toDtoPlain)
                     .collect(Collectors.toList());
  }

  public TagBrowseDto getTree(String path, long userId){
    path = LtreeUtils.escape(path);
    List<TagBrowseDto> list = repository.getTagTree(path,userId);
    TagBrowseDto root = TreeUtils.buildTree(list,path);
    return root;
  }

/*
  @Transactional
  public TagDto create(TagDto dto, long userId) throws WebApplicationException{
    Tag entity = mapper.toEntity(dto);
    entity.setUser(userRepository.findById(userId));
    repository.persist(entity);
    return mapper.toDto(entity);
  }
*/

  @Transactional
  public TagDto createIfNotExists(TagDto dto, long userId)  {
      Tag entity = repository.findByTagAndUserID(dto.value(), dto.user().id());
      if(entity == null){
        entity = mapper.toEntity(dto);
        entity.setUser(userRepository.findById(userId));
        repository.persist(entity);
      }
      return mapper.toDto(entity);
    }


  @Transactional
  public long delete(Long id, long userId) {
    return repository.deleteByIdAndUserID(id,userId);
  }

  @Transactional
  public long deleteByBookmarkIDAndUserId(Long bookmarkId, long userId) {
    return repository.deleteByBookmarkIDAndUserID(bookmarkId,userId);
  }


  @Transactional
  public TagDto update(long id,TagDto dto, long userId) throws WebApplicationException {
    if(id != dto.id()){
      throw new NotAllowedException(String.format("id in tag %s and given id %s are different: ", id,dto.id()));
    }
    Tag entity = repository.findById(id);
    if(entity == null){
      throw new NotFoundException(String.format("No Tag found with id[%s]", dto.id()));
    }
    if(entity.getUser().getId() != userId){
      throw new NotAllowedException(String.format("User with id[%s]  is not allowed to update tag with id %s", userId,id));
    }
    mapper.update(dto, entity);
    repository.persist(entity);
    return mapper.toDto(entity);
  }
}