package org.mercuriusb.collectio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDto;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDtoBuilder;
import org.mercuriusb.collectio.dto.bookmark.BookmarkExtractDto;
import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDto;
import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDtoBuilder;
import org.mercuriusb.collectio.dto.tag.TagDto;
import org.mercuriusb.collectio.dto.tag.TagDtoBuilder;
import org.mercuriusb.collectio.dto.user.UserDto;
import org.mercuriusb.collectio.mapper.BookmarkMapper;
import org.mercuriusb.collectio.mapper.TagMapper;
import org.mercuriusb.collectio.model.Bookmark;
import org.mercuriusb.collectio.repository.BookmarkMetaDataRepository;
import org.mercuriusb.collectio.repository.BookmarkRepository;
import org.mercuriusb.collectio.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookmarkService{
  private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

  @Inject
  Logger logger;
  @Inject
  BookmarkRepository repository;
  @Inject
  TagService tagService;
  @Inject
  BookmarkMetaDataService metaDataService;
  @Inject
  BookmarkMapper mapper;
  @Inject
  TagMapper tagMapper;
  @Inject
  UserService userService;
  @Inject
  ObjectMapper objectMapper;
  @Channel("url_extract")
  Emitter<BookmarkExtractDto> urlExtractEmitter;

  public BookmarkDto getById(Long id,long userId) throws WebApplicationException{
    Bookmark bookmark = repository.findById(id);
    if(bookmark == null){
      throw new NotFoundException(String.format("Bookmark with id %s not found",id));
    }
    return mapper.toDto(bookmark);
  }

  public List<BookmarkDto> getAll(long userId) throws WebApplicationException{
    return repository.listAll().stream()
                     .map(mapper::toDto)
                     .collect(Collectors.toList());
  }

  @Transactional
  public BookmarkDto createOrUpdate(BookmarkDto dto, long userId) throws WebApplicationException{
    return createOrUpdate(dto, userId, false);
  }

    @Transactional
  public BookmarkDto createOrUpdate(BookmarkDto dto, long userId,boolean clearExistingTags) throws WebApplicationException{
    Bookmark entity = repository.findByURL(dto.getUrl());
    if(entity == null){
      entity = mapper.toEntity(dto);
    }else{
      logger.info("Bookmark already exists");
      metaDataService.deleteByBookmarkIDAndUserId(entity.getId(), userId);
      tagService.deleteByBookmarkIDAndUserId(entity.getId(),userId);
      mapper.update(dto, entity);
      /*
      try{
        logger.info("Bookmark updated: " + objectMapper.writeValueAsString(mapper.toDto(entity)));
      }catch(JsonProcessingException e){
        throw new RuntimeException(e);
      }
      */
    }
    try{
      repository.persist(entity);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
    urlExtractEmitter.send(mapper.toBookmarkExtractDto(entity));
    return mapper.toDto(entity);
  }

  @Transactional
  public boolean delete(Long id, long userId) throws WebApplicationException{
    metaDataService.deleteByBookmarkIDAndUserId(id, userId);
    tagService.deleteByBookmarkIDAndUserId(id,userId);
    //TODO really delete? better to delete all bookmarks without tags and metadata later
    return repository.deleteById(id);
  }



  @Transactional
  public BookmarkDto update(long id,BookmarkDto dto) throws WebApplicationException {
    Bookmark entity = repository.findById(id);
    if(entity == null){
      throw new NotFoundException(String.format("No Bookmark found with id[%s]", dto.getId()));
    }
    mapper.update(dto, entity);
    repository.persist(entity);
    return mapper.toDto(entity);
  }

  @Transactional
  public void importBookmarks(String bookmarkHTML,long userId) throws WebApplicationException{
    UserDto userDto = userService.getById(userId);
    Document doc = Jsoup.parse(bookmarkHTML);

    Element root = doc.body();
    TagDto currentTagDto = new TagDtoBuilder()
        .setUser(userDto)
        .setValue("root")
        .createTagDto();
    currentTagDto = tagService.createIfNotExists(currentTagDto,userDto.getId());
    parseFolder(root, "root", userDto, currentTagDto);

  }

  private void parseFolder(Element element, String currentFolder, UserDto userDto, TagDto currentTagDto) {
    Elements elements = element.children();
    for (Element el : elements) {
      //log.info("Element: " + el.tagName() );
      if(el.tagName().equalsIgnoreCase("dt")){
        String tempFolder = currentFolder;
        for(Element child : el.children()){
          if(child.tagName().equalsIgnoreCase("h3")){
            log.info("Folder: " + tempFolder);
            tempFolder = tempFolder + "/" + child.text();
            currentTagDto = new TagDtoBuilder()
                .setUser(userDto)
                .setValue(tempFolder)
                .createTagDto();
            currentTagDto = tagService.createIfNotExists(currentTagDto,userDto.getId());
          }else if(child.tagName().equalsIgnoreCase("a")){
            String title = child.text();
            String url = child.attr("HREF");
            BookmarkMetaDataDto bookmarkMetaDataDto = new BookmarkMetaDataDtoBuilder()
                .setUser(userDto)
                .setTitle(title)
                .createBookmarkMetaDataDto();
            BookmarkDto bookmarkDto = new BookmarkDtoBuilder()
                .setOriginalTitle(title)
                .setUrl(url)
                .createBookmarkDto();
            bookmarkDto.getTags().add(currentTagDto);
            bookmarkDto.getBookmarkMetaData().add(bookmarkMetaDataDto);
            //log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            bookmarkDto = createOrUpdate(bookmarkDto, userDto.getId(),false);
            //log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            log.info("bookmark: " + currentFolder + ":" + title); // + ":" + url);
          }else if(child.tagName().equalsIgnoreCase("dl")){
            parseFolder(child, tempFolder,userDto,currentTagDto);
          }
        }
      }
    }
  }

}