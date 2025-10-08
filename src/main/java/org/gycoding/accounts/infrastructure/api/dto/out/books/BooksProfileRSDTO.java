package org.gycoding.accounts.infrastructure.api.dto.out.books;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksProfileRSDTO(
    UUID id,
    String username,
    String email,
    String phoneNumber,
    List<AccountRoles> roles,
    String apiKey,
    String picture,
    Boolean isFriend,
    String biography
) { }