package org.gycoding.accounts.infrastructure.external.database.model.metadata;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "Metadata")
public class MetadataEntity {
    @Id
    private String mongoId;
    private String userId;
    private ProfileEntity profile;
}