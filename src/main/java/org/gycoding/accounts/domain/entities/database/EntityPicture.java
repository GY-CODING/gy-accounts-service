package org.gycoding.accounts.domain.entities.database;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.BSONObject;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Builder
@Getter
@Setter
@Document(collection = "AuthPicture")
public class EntityPicture {
    @Id
    private String mongoId;
    private String name;
    private String contentType;
    private Binary picture;
}