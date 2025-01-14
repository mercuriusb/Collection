package org.mercuriusb.collectio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO for {@link org.mercuriusb.collectio.model.User}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RecordBuilder
public record UserDto(
    Long id,
    UUID uuid,
    OffsetDateTime created,
    OffsetDateTime modified,
    OffsetDateTime deleted,
    String name,
    String password,
    String email) implements Serializable{}

