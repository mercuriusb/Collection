package org.mercuriusb.collectio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.query.Query;
import org.hibernate.query.ResultListTransformer;
import org.jboss.logging.Logger;
import org.mercuriusb.collectio.dto.bookmark.BookmarkViewDto;
import org.mercuriusb.collectio.dto.bookmark.BookmarkViewDtoBuilder;
import org.mercuriusb.collectio.dto.tag.TagBrowseDto;
import org.mercuriusb.collectio.dto.tag.TagDto;
import org.mercuriusb.collectio.dto.tag.TagDtoBuilder;
import org.mercuriusb.collectio.model.Tag;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;

@SuppressWarnings("ALL")
@ApplicationScoped
public class TagRepository implements PanacheRepository<Tag>{
  private static final Logger log = Logger.getLogger(TagRepository.class);

  public long deleteByBookmarkIDAndUserID(long bookmarkID, long userID) {
    return delete("DELETE FROM Tag t WHERE t.user.id = ?2 AND EXISTS (SELECT b FROM t.bookmarks b WHERE b.id = ?1)", bookmarkID, userID);
  }

  public Tag findByIdAndUserID(long id, long userID) {
    return find("id = ?1 and user.id = ?2", id, userID).firstResult();
  }

  public long deleteByIdAndUserID(long id, long userID) {
    return delete("id = ?1 and user.id = ?2", id, userID);
  }



  public Tag findByTagAndUserID(String tag, long userID) {
    return find("value = ?1 and user.id = ?2", tag, userID).firstResult();
  }

  public List<Tag> listAllByUserID(long userID) {
    List<Tag> tags = list("user.id = ?1", userID);
    return tags;
  }

  public List<TagBrowseDto> getTagTree(String path, long userId) {
    List<TagBrowseDto> dtos = getEntityManager().createNativeQuery(String.format(
        """
        select ct.id,ct.uuid, ct.value, ct.path, count(distinct(cbt.bookmark_id)) from col_tag ct
        left join col_bookmark_tags cbt on cbt.tags_id = ct.id
        where (ct.path ~ '%s.*{1}' or ct.path ~ '%s') and ct.user_id = %d
        group by ct.id
        order by ct.path
        """,path,path,userId))
        .unwrap(Query.class).setTupleTransformer(
        (tuple,aliases) -> {
          System.out.println("Tuple:" + tuple);
          return new TagBrowseDto(
              (Long) tuple[0],          //id
              (UUID) tuple[1],          //uuid
              (String) tuple[2],        //value
              tuple[3] != null ? tuple[3].toString() : "",    //path
              ((Number) tuple[4]).intValue(),          //count
              new LinkedHashSet<>()    //bookmarks
          );
        }
    ).getResultList();

    List<Object[]> tuples = getEntityManager().createNativeQuery(String.format(
        """
            select cb.id, cb."uuid",cb.created,cb.modified,cb.last_viewed, cb.url,cb.original_title,cb.summary,cbmd.title,cbmd.description,cbmd.state,ct.id,ct."uuid",ct.value from col_bookmark cb
            join col_bookmark_tags cbt on cbt.bookmark_id = cb.id
            join col_bookmark_meta_data cbmd on cbmd.bookmark_id  = cb.id
            join col_tag ct on ct.id  = cbt.tags_id
            where ct.user_id = 0 and cbmd.user_id = %d
            and ct."path" ~ '%s' order by cbmd.title
        """,userId,path)).getResultList();
    Map<Long,BookmarkViewDto> bookmarkViewDtoMap = new HashMap<>();
    for(Object[] tuple : tuples){
      Long id = (Long) tuple[0];          //id
      BookmarkViewDto dto = null;
      if(bookmarkViewDtoMap.containsKey(id)){
        dto = bookmarkViewDtoMap.get(id);
        Long tagId = (Long) tuple[11];      //tag id
        UUID tagUUID = (UUID) tuple[12];        //tag uuid
      }else{
        UUID uuid = (UUID) tuple[1];          //uuid
        Instant created = (Instant) tuple[2];        //created
        Instant modified = (Instant) tuple[3];        //modified
        Instant lastviewed = (Instant) tuple[4];        //lastviewed
        String url = (String) tuple[5];        //url
        String originalTitle = (String) tuple[6];        //original title
        String summary = (String) tuple[7];        //summary
        String title = (String) tuple[8];        //title
        String description = (String) tuple[9];        //description
        String state = (String) tuple[10];        //state
        dto = new BookmarkViewDtoBuilder()
            .setId(id)
            .setUuid(uuid)
            .setCreated(created != null ? OffsetDateTime.ofInstant(created, ZoneId.systemDefault()) : null)
            .setModified(modified != null ?  OffsetDateTime.ofInstant(modified, ZoneId.systemDefault()) : null)
            .setLastViewed(lastviewed != null ? OffsetDateTime.ofInstant(lastviewed, ZoneId.systemDefault()) : null)
            .setUrl(url)
            .setOriginalTitle(originalTitle)
            .setSummary(summary)
            .setTitle(title)
            .setDescription(description)
            .setState(state)
            .setTags(new LinkedHashSet<>())
            .createBookmarkViewDto();
        bookmarkViewDtoMap.put(id,dto);
      }
      Long tagId = (Long) tuple[11];      //tag id
      UUID tagUUID = (UUID) tuple[12];        //tag uuid
      String tagValue = (String) tuple[13];        //tag value
      TagDto tagDto = new TagDtoBuilder().setId(tagId).setUuid(tagUUID).setValue(tagValue).createTagDto();
      dto.getTags().add(tagDto);
    }
    for(TagBrowseDto dto : dtos){
      if(dto.getPath().equals(path)){
        dto.getBookmarks().addAll(bookmarkViewDtoMap.values());
        break;
      }
    }
    return dtos;
  }



}