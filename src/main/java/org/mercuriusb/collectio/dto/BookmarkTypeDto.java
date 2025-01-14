package org.mercuriusb.collectio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.BookmarkType}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RecordBuilder
public record BookmarkTypeDto(
    Long id,
    UUID uuid,
    OffsetDateTime created,
    OffsetDateTime modified,
    OffsetDateTime deleted,
    String value) implements Serializable{}