package org.mercuriusb.collectio.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDto;
import org.mercuriusb.collectio.mapper.BookmarkMetaDataMapper;
import org.mercuriusb.collectio.model.BookmarkMetaData;
import org.mercuriusb.collectio.repository.BookmarkMetaDataRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookmarkMetaDataService{

  @Inject
  BookmarkMetaDataRepository repository;
  @Inject
  BookmarkMetaDataMapper mapper;

  public BookmarkMetaDataDto getById(Long id){
    return mapper.toDto(repository.findById(id));
  }

  public List<BookmarkMetaDataDto> getAll(){
    return repository.listAll().stream()
                     .map(mapper::toDto)
                     .collect(Collectors.toList());
  }

  @Transactional
  public BookmarkMetaData create(BookmarkMetaDataDto dto){
    BookmarkMetaData entity = mapper.toEntity(dto);
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
    BookmarkMetaData entity = repository.findById(id);
    if(entity == null){
      throw new NotFoundException(String.format("No BookmarkMetaData found with id[%s]", dto.getId()));
    }
    mapper.update(dto, entity);
    repository.persist(entity);
    return mapper.toDto(entity);
  }
}