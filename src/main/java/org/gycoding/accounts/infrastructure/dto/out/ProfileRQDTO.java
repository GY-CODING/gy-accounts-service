package org.gycoding.accounts.infrastructure.dto.out;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ProfileRQDTO(
    String email,
    String username,
    String phoneNumber,
    MultipartFile picture
) { }