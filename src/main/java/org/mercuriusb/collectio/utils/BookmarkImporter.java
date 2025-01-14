package org.mercuriusb.collectio.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mercuriusb.collectio.dto.BookmarkDto;
import org.mercuriusb.collectio.dto.BookmarkDtoBuilder;
import org.mercuriusb.collectio.dto.BookmarkMetaDataDto;
import org.mercuriusb.collectio.dto.BookmarkMetaDataDtoBuilder;
import org.mercuriusb.collectio.dto.BookmarkTypeDto;
import org.mercuriusb.collectio.dto.TagDto;
import org.mercuriusb.collectio.dto.TagDtoBuilder;
import org.mercuriusb.collectio.dto.UserDto;
import org.mercuriusb.collectio.service.BookmarkService;
import org.mercuriusb.collectio.service.BookmarkTypeService;
import org.mercuriusb.collectio.service.TagService;
import org.mercuriusb.collectio.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

@ApplicationScoped
public class BookmarkImporter{
  private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

  @Inject
  BookmarkService bookmarkService;

  BookmarkTypeService bookmarkTypeService;
  @Inject
  TagService tagService;
  @Inject
  UserService userService;

  private BookmarkTypeDto bookmarkTypeDto;

  public BookmarkImporter(BookmarkTypeService bookmarkTypeService) {
    this.bookmarkTypeService = bookmarkTypeService;
    bookmarkTypeDto = bookmarkTypeService.getAll().getFirst();
  }

  public void importSafariBookmarks(String path) {
    try {
      UserDto userDto = userService.getAll().getFirst();


      File inputFile = new File(path);
      Document doc = Jsoup.parse(inputFile, "UTF-8");

      Element root = doc.body();
      TagDto currentTagDto = TagDtoBuilder.builder()
          .user(userDto)
          .value("root")
          .build();
      currentTagDto = tagService.createIfNotExists(currentTagDto,userDto.id());
      parseFolder(root, "root", userDto, currentTagDto);
      System.out.println("TEST");
    } catch (IOException e) {
      e.printStackTrace();
    }
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
            /*
            tempFolder = tempFolder.replaceAll("\\.","_");
            String path = tempFolder.replaceAll("/","." );
            path = path.replaceAll("[^\\.a-zA-Z0-9_]","_");
             */
            currentTagDto = TagDtoBuilder.builder()
                .user(userDto)
                .value(tempFolder)
                .build();
            currentTagDto = tagService.createIfNotExists(currentTagDto,userDto.id());
          }else if(child.tagName().equalsIgnoreCase("a")){
            String title = child.text();
            String url = child.attr("HREF");
            BookmarkMetaDataDto bookmarkMetaDataDto = BookmarkMetaDataDtoBuilder.builder()
                .user(userDto)
                .title(title)
                .build();
            BookmarkDto bookmarkDto = BookmarkDtoBuilder.builder()
                .originalTitle(title)
                .url(url)
                .type(bookmarkTypeDto)
                .build();
            bookmarkDto.tags().add(currentTagDto);
            bookmarkDto.bookmarkMetaData().add(bookmarkMetaDataDto);
            //log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            bookmarkDto = bookmarkService.createOrUpdate(bookmarkDto, userDto.id(),false);
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
