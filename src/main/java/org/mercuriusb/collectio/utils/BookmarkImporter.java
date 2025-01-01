package org.mercuriusb.collectio.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDto;
import org.mercuriusb.collectio.dto.bookmark.BookmarkDtoBuilder;
import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDto;
import org.mercuriusb.collectio.dto.bookmarkmetadata.BookmarkMetaDataDtoBuilder;
import org.mercuriusb.collectio.dto.bookmarktype.BookmarkTypeDto;
import org.mercuriusb.collectio.dto.tag.TagDto;
import org.mercuriusb.collectio.dto.tag.TagDtoBuilder;
import org.mercuriusb.collectio.dto.user.UserDto;
import org.mercuriusb.collectio.service.BookmarkService;
import org.mercuriusb.collectio.service.BookmarkTypeService;
import org.mercuriusb.collectio.service.TagService;
import org.mercuriusb.collectio.service.UserService;

import java.io.File;
import java.io.IOException;

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
      TagDto currentTagDto = new TagDtoBuilder()
          .setUser(userDto)
          .setValue("root")
          .createTagDto();
      currentTagDto = tagService.createIfNotExists(currentTagDto,userDto.getId());
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
                .setType(bookmarkTypeDto)
                .createBookmarkDto();
            bookmarkDto.getTags().add(currentTagDto);
            bookmarkDto.getBookmarkMetaData().add(bookmarkMetaDataDto);
            //log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            bookmarkDto = bookmarkService.createOrUpdate(bookmarkDto, userDto.getId(),false);
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
