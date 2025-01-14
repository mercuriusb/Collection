package org.mercuriusb.collectio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.Bookmark}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RecordBuilder
public record BookmarkExtractDto(
    Long id,
    UUID uuid,
    String url,
    String typeTypeValue
) implements Serializable{}