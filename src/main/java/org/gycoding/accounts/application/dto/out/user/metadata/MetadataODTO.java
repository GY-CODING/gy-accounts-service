package org.gycoding.accounts.application.dto.out.user.metadata;

import lombok.*;
import org.gycoding.accounts.application.dto.out.user.metadata.books.BooksMetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.gymessages.GYMessagesMetadataODTO;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataODTO {
    private BooksMetadataODTO books;
    private GYMessagesMetadataODTO gyMessages;
    private ProfileODTO profile;
}