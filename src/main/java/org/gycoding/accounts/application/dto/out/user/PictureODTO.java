package org.gycoding.accounts.application.dto.out.user;

import lombok.Builder;
import org.bson.types.Binary;

@Builder
public record PictureODTO(
        String name,
        String contentType,
        Binary picture
) { }