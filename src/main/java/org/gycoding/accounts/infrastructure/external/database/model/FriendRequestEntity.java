package org.gycoding.accounts.infrastructure.external.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "FriendRequest")
public class FriendRequestEntity {
    @Id
    private String mongoId;
    private String id;
    private String from;
    private String to;
}
