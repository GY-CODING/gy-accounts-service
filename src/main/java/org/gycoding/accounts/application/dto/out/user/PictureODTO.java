package org.gycoding.accounts.application.dto.out.user;

import lombok.Builder;
import org.bson.types.Binary;
import org.gycoding.accounts.shared.GYCODINGRoles;

import java.util.List;

@Builder
public record PictureODTO(
        String name,
        String contentType,
        Binary picture
) { }