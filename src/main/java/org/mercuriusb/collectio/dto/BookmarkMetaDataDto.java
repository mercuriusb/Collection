package org.mercuriusb.collectio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.soabase.recordbuilder.core.RecordBuilder;
import org.mercuriusb.collectio.model.BookmarkUserMetaData;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link BookmarkUserMetaData}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RecordBuilder
public record BookmarkMetaDataDto(
    Long id,
    String title,
    String description,
    String state,
    BookmarkDto bookmark,
    UserDto user) implements Serializable{}
