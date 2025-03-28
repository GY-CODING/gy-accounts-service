package org.gycoding.accounts.infrastructure.external.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "ApiKey")
public class ApiKeyEntity {
    @Id
    private String mongoId;
    private String apiKey;
    private String userId;
}