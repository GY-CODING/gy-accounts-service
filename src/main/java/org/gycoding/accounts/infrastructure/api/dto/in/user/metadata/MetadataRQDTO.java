package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.messages.MessagesMetadataRQDTO;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Builder
@Getter
@Setter
public class MetadataRQDTO {
    private List<AccountRoles> roles;
    private MessagesMetadataRQDTO messages;
}