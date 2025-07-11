package org.gycoding.accounts.infrastructure.external.database.model.metadata.books;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class BooksMetadataEntity {
    private List<String> friends;
}
