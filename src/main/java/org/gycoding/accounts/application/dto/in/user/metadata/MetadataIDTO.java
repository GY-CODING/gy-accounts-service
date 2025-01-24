package org.gycoding.accounts.application.dto.in.user.metadata;

import lombok.*;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataIDTO {
    public List<AccountRoles> roles;
}