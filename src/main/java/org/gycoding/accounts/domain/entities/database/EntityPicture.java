package org.gycoding.accounts.domain.entities.database;

import lombok.Builder;
import org.bson.BSONObject;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Builder
@Document(collection = "AuthPicture")
public record EntityPicture(
    @Id
    String mongoId,
    String name,
    String contentType,
    Binary picture
) { }