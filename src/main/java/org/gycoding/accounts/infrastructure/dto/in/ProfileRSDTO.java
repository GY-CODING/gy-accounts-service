package org.gycoding.accounts.infrastructure.dto.in;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ProfileRSDTO(
    String email,
    String username,
    String phoneNumber,
    MultipartFile picture
) { }