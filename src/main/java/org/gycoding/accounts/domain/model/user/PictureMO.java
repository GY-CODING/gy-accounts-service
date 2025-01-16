package org.gycoding.accounts.domain.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
public record PictureMO (
    String name,
    String contentType,
    Binary picture
) {}