package org.gycoding.accounts.infrastructure.external.database.model.metadata;

import lombok.*;
import org.gycoding.accounts.shared.AccountRoles;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {
    @Field("id")
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private List<AccountRoles> roles;
    private String apiKey;
    private String picture;
}