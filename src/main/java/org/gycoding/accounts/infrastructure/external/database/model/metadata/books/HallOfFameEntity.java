package org.gycoding.accounts.infrastructure.external.database.model.metadata.books;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class HallOfFameEntity {
    private List<String> books;
    private String quote;
}
