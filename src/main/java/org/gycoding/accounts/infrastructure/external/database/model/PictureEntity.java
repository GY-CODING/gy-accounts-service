package org.gycoding.accounts.infrastructure.external.database.model;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "AuthPicture")
public class PictureEntity {
    @Id
    private String mongoId;
    private String name;
    private String contentType;
    private Binary picture;
}