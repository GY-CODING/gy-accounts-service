package org.gycoding.accounts.infrastructure.external.auth.mapper;

import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.ChatMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.GYMessagesMetadataMO;
import org.gycoding.accounts.shared.AccountRoles;
import org.gycoding.accounts.shared.utils.Base64Utils;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthFacadeMapper {
    default Map<String, Object> toMap(MetadataMO metadata) {
        return Map.of(
                "books", Map.of(
                        "friends", metadata.getBooks().friends()
                ),
                "gyMessages", Map.of(
                        "chats", metadata.getGyMessages().chats()
                ),
                "profile", Map.of(
                        "id", metadata.getProfile().id(),
                        "username", metadata.getProfile().username(),
                        "picture", metadata.getProfile().picture(),
                        "roles", metadata.getProfile().roles(),
                        "apiKey", metadata.getProfile().apiKey(),
                        "phoneNumber", metadata.getProfile().phoneNumber()
                )
        );
    }

    default MetadataMO toMO(Map<String, Object> metadata) {
        return MetadataMO.builder()
                .books(this.booksMetadataToMO(metadata))
                .gyMessages(this.messagesMetadataToMO(metadata))
                .profile(this.profileToMO(metadata))
                .build();
    }

    private BooksMetadataMO booksMetadataToMO(Map<String, Object> metadata) {
        final var defaultBooksMetadata = BooksMetadataMO.builder()
                .friends(List.of())
                .build();

        try {
            return BooksMetadataMO.builder()
                    .friends(
                            ((List<String>) ((Map<String, Object>) metadata.get("books")).get("friends")).stream()
                                    .toList()
                    )
                    .build();
        } catch(Exception e) {
            return defaultBooksMetadata;
        }
    }

    private GYMessagesMetadataMO messagesMetadataToMO(Map<String, Object> metadata) {
        final var defaultGYMessagesMetadata = GYMessagesMetadataMO.builder()
                .chats(List.of())
                .build();

        try {
            return GYMessagesMetadataMO.builder()
                    .chats(
                            ((List<Map<String, Object>>) ((Map<String, Object>) metadata.get("gyMessages")).get("chats")).stream()
                                .map(this::chatToMO)
                                .toList()
                    )
            .build();
        } catch(Exception e) {
            return defaultGYMessagesMetadata;
        }
    }

    private ChatMO chatToMO(Map<String, Object> chat) {
        return ChatMO.builder()
                .chatId((String) chat.get("chatId"))
                .isAdmin((Boolean) chat.get("isAdmin"))
                .build();
    }

    private ProfileMO profileToMO(Map<String, Object> metadata) {
        final var defaultProfile = ProfileMO.builder()
                .id(UUID.randomUUID())
                .picture("")
                .username("")
                .phoneNumber("+01 123456789")
                .roles(List.of(AccountRoles.COMMON))
                .apiKey(Base64Utils.generateApiKey())
                .build();

        try {
            return ProfileMO.builder()
                    .id(
                            UUID.fromString(
                                    (String) ((HashMap<String, Object>) metadata.get("profile"))
                                    .getOrDefault("id", defaultProfile.id().toString())
                            )
                    )
                    .username(
                            (String) ((HashMap<String, Object>) metadata.get("profile"))
                                    .getOrDefault("username", defaultProfile.username())
                    )
                    .phoneNumber(
                            (String) ((HashMap<String, Object>) metadata.get("profile"))
                                    .getOrDefault("phoneNumber", defaultProfile.phoneNumber())
                    )
                    .picture(
                            (String) ((HashMap<String, Object>) metadata.get("profile"))
                                    .getOrDefault("picture", defaultProfile.picture())
                    )
                    .roles(
                            ((List<String>) ((HashMap<String, Object>) metadata.get("profile"))
                                    .getOrDefault("roles", List.of(AccountRoles.COMMON.getRole()))).stream()
                                        .map(AccountRoles::fromString)
                                        .toList()
                    )
                    .apiKey(
                            (String) ((HashMap<String, Object>) metadata.get("profile"))
                                    .getOrDefault("apiKey", defaultProfile.apiKey())
                    )
                    .build();
        } catch(Exception e) {
            return defaultProfile;
        }
    }
}
