package org.mercuriusb.collectio.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.mercuriusb.collectio.dto.bookmarktype.BookmarkTypeDto;
import org.mercuriusb.collectio.mapper.BookmarkTypeMapper;
import org.mercuriusb.collectio.model.BookmarkType;
import org.mercuriusb.collectio.repository.BookmarkTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookmarkTypeService{

  @Inject
  BookmarkTypeRepository repository;
  @Inject
  BookmarkTypeMapper mapper;

  public BookmarkTypeDto getById(Long id){
    return mapper.toDto(repository.findById(id));
  }

  public List<BookmarkTypeDto> getAll(){
    return repository.listAll().stream()
                     .map(mapper::toDto)
                     .collect(Collectors.toList());
  }

  @Transactional
  public BookmarkType create(BookmarkTypeDto dto){
    BookmarkType entity = mapper.toEntity(dto);
    repository.persist(entity);
    return entity;
  }

  @Transactional
  public boolean delete(Long id){
    return repository.deleteById(id);
  }

  @Transactional
  public BookmarkTypeDto update(long id, BookmarkTypeDto dto) throws NotFoundException{
    BookmarkType entity = repository.findById(id);
    if(entity == null){
      throw new NotFoundException(String.format("No BookmarkType found with id[%s]", dto.getId()));
    }
    mapper.update(dto, entity);
    repository.persist(entity);
    return mapper.toDto(entity);
  }
}