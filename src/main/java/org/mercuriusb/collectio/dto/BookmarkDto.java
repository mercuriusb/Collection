package org.mercuriusb.collectio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.Bookmark}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RecordBuilder
public record BookmarkDto(
    Long id,
    UUID uuid,
    OffsetDateTime created,
    OffsetDateTime modified,
    OffsetDateTime deleted,
    String url,
    String content,
    String mimetype,
    String originalTitle,
    String plaintext,
    String summary,
    Set<TagDto> tags,
    Set<BookmarkMetaDataDto> bookmarkMetaData,
    BookmarkTypeDto type,
    OffsetDateTime lastViewed,
    OffsetDateTime lastChecked) implements Serializable{
  public BookmarkDto {
        if (tags == null) {
            tags = new LinkedHashSet<>();
        }
        if (bookmarkMetaData == null) {
            bookmarkMetaData = new LinkedHashSet<>();
        }
    }
}
