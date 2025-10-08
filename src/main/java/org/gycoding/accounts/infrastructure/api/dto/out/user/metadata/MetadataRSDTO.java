package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataRSDTO {
    private ProfileRSDTO profile;
}