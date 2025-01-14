package org.mercuriusb.collectio.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.mercuriusb.collectio.dto.BookmarkMetaDataDto;
import org.mercuriusb.collectio.mapper.BookmarkUserMetaDataMapper;
import org.mercuriusb.collectio.model.BookmarkUserMetaData;
import org.mercuriusb.collectio.repository.BookmarkMetaDataRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookmarkMetaDataService{

  @Inject
  BookmarkMetaDataRepository repository;
  @Inject
  BookmarkUserMetaDataMapper mapper;

  public BookmarkMetaDataDto getById(Long id){
    return mapper.toDto(repository.findById(id));
  }

  public List<BookmarkMetaDataDto> getAll(){
    return repository.listAll().stream()
                     .map(mapper::toDto)
                     .collect(Collectors.toList());
  }

  @Transactional
  public BookmarkUserMetaData create(BookmarkMetaDataDto dto){
    BookmarkUserMetaData entity = mapper.toEntity(dto);
    repository.persist(entity);
    return entity;
  }

  @Transactional
  public boolean delete(Long id){
    return repository.deleteById(id);
  }

  @Transactional
  public long deleteByBookmarkIDAndUserId(Long bookmarkId, long userId){
    return repository.deleteByBookmarkIDAndUserId(bookmarkId,userId);
  }


  @Transactional
  public BookmarkMetaDataDto update(long id, BookmarkMetaDataDto dto) throws NotFoundException{
    BookmarkUserMetaData entity = repository.findById(id);
    if(entity == null){
      throw new NotFoundException(String.format("No BookmarkUserMetaData found with id[%s]", dto.id()));
    }
    mapper.update(dto, entity);
    repository.persist(entity);
    return mapper.toDto(entity);
  }
}