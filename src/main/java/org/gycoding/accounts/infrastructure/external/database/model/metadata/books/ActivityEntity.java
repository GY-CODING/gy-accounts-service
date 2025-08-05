package org.gycoding.accounts.infrastructure.external.database.model.metadata.books;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ActivityEntity {
    private String id;
    private String message;
    private Date date;
}
