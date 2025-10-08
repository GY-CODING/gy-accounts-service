package org.gycoding.accounts.application.dto.out.user.metadata;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataODTO {
    private ProfileODTO profile;
}