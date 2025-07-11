package org.gycoding.accounts.application.dto.out.user.metadata;

import lombok.*;
import org.gycoding.accounts.application.dto.out.user.metadata.books.BooksMetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.messages.MessagesMetadataODTO;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataODTO {
    private BooksMetadataODTO books;
    private MessagesMetadataODTO messages;
    private ProfileODTO profile;
}