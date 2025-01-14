package org.mercuriusb.collectio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.Tag}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RecordBuilder
public record TagDto(
    Long id,
    UUID uuid,
    OffsetDateTime created,
    OffsetDateTime modified,
    OffsetDateTime deleted,
    String value,
    Set<BookmarkDto> bookmarks,
    UserDto user) implements Serializable{
  public TagDto {
        if (bookmarks == null) {
            bookmarks = new LinkedHashSet<>();
        }
    }
}
