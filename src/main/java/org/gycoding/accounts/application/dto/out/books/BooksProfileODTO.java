package org.gycoding.accounts.application.dto.out.books;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksProfileODTO(
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