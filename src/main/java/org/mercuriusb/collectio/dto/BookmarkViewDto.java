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
public record BookmarkViewDto(Long id,
                              UUID uuid,
                              OffsetDateTime created,
                              OffsetDateTime modified,
                              OffsetDateTime lastViewed,
                              String url,
                              String originalTitle,
                              String summary,
                              String typeValue,
                              String title,
                              String description,
                              String state,
                              Set<TagDto> tags
) implements Serializable{
  public BookmarkViewDto {
        if (tags == null) {
            tags = new LinkedHashSet<>();
        }
    }
}
