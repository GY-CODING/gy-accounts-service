package org.gycoding.accounts.domain.model.user.metadata;

import lombok.*;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.GYMessagesMetadataMO;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataMO {
    private BooksMetadataMO books;
    private GYMessagesMetadataMO gyMessages;
    private ProfileMO profile;
}