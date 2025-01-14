
package org.mercuriusb.collectio.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.mercuriusb.collectio.dto.UserDto;
import org.mercuriusb.collectio.mapper.UserMapper;
import org.mercuriusb.collectio.model.User;
import org.mercuriusb.collectio.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService{

  @Inject
  UserRepository repository;
  @Inject
  UserMapper mapper;

  public UserDto getById(Long id){
    return mapper.toDto(repository.findById(id));
  }

  public List<UserDto> getAll(){
    return repository.listAll().stream()
                     .map(mapper::toDto)
                     .collect(Collectors.toList());
  }

  @Transactional
  public User create(UserDto dto) {
      User entity = mapper.toEntity(dto);
      repository.persist(entity);
      return entity;
  }

  @Transactional
  public boolean delete(Long id){
    return repository.deleteById(id);
  }

  @Transactional
  public UserDto update(long id,UserDto dto) throws NotFoundException {
    User entity = repository.findById(id);
    if(entity == null){
      throw new NotFoundException(String.format("No User found with id[%s]", dto.id()));
    }
    mapper.update(dto, entity);
    repository.persist(entity);
    return mapper.toDto(entity);
  }

  @Transactional
  public UserDto ensureUserExistsAndGet(String username) {
    // Check if the user exists
    User user = repository.findByName (username);
    if (user == null) {
      // If not, create the user
      user = new User();
      user.setName(username);
      //user.keycloakId = keycloakId;
      repository.persist(user);
    }
    return mapper.toDto(user); // Return the user for further use
  }

}