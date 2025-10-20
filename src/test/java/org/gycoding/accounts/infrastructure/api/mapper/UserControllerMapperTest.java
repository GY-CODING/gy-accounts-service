package org.gycoding.accounts.infrastructure.api.mapper;

import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.MetadataRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.ProfileRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.ProfileRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.PublicProfileRSDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserControllerMapperTest {
    @InjectMocks
    private UserControllerMapperImpl mapper;

    @Test
    @DisplayName("[USER_CONTROLLER_MAPPER] - Test successful mapping from ProfileODTO to ProfileRSDTO.")
    void testProfileToRSDTO() {
        // When
        final var profileODTO = mock(ProfileODTO.class);
        final var profileRSDTO = mock(ProfileRSDTO.class);

        // Then
        final var result = mapper.toRSDTO(profileODTO);

        // Verify
        assertEquals(profileRSDTO.id(), result.id());
        assertEquals(profileRSDTO.username(), result.username());
        assertEquals(profileRSDTO.email(), result.email());
        assertEquals(profileRSDTO.phoneNumber(), result.phoneNumber());
        assertEquals(profileRSDTO.roles(), result.roles());
        assertEquals(profileRSDTO.apiKey(), result.apiKey());
        assertEquals(profileRSDTO.picture(), result.picture());
    }

    @Test
    @DisplayName("[USER_CONTROLLER_MAPPER] - Test unsuccessful mapping from ProfileODTO to ProfileRSDTO.")
    void testWrongProfileToRSDTO() {
        // When
        final ProfileODTO profileODTO = null;

        // Then
        final var result = mapper.toRSDTO(profileODTO);

        // Verify
        assertNull(result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER_MAPPER] - Test successful mapping from ProfileRQDTO to ProfileIDTO.")
    void testProfileToIDTO() {
        // When
        final var profileRQDTO = mock(ProfileRQDTO.class);
        final var profileIDTO = mock(ProfileIDTO.class);

        // Then
        final var result = mapper.toIDTO(profileRQDTO);

        // Verify
        assertEquals(profileIDTO.username(), result.username());
        assertEquals(profileIDTO.phoneNumber(), result.phoneNumber());
        assertEquals(profileIDTO.picture(), result.picture());
    }

    @Test
    @DisplayName("[USER_CONTROLLER_MAPPER] - Test unsuccessful mapping from ProfileRQDTO to ProfileIDTO.")
    void testWrongProfileToIDTO() {
        // When
        final ProfileRQDTO profileRQDTO = null;

        // Then
        final var result = mapper.toIDTO(profileRQDTO);

        // Verify
        assertNull(result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER_MAPPER] - Test successful mapping from ProfileODTO to PublicProfileRSDTO.")
    void testProfileToPublicRSDTO() {
        // When
        final var profileODTO = mock(ProfileODTO.class);
        final var publicProfileRSDTO = mock(PublicProfileRSDTO.class);

        // Then
        final var result = mapper.toPublicRSDTO(profileODTO);

        // Verify
        assertEquals(publicProfileRSDTO.id(), result.id());
        assertEquals(publicProfileRSDTO.username(), result.username());
        assertEquals(publicProfileRSDTO.email(), result.email());
        assertEquals(publicProfileRSDTO.phoneNumber(), result.phoneNumber());
        assertEquals(publicProfileRSDTO.picture(), result.picture());
    }

    @Test
    @DisplayName("[USER_CONTROLLER_MAPPER] - Test unsuccessful mapping from ProfileODTO to PublicProfileRSDTO.")
    void testWrongProfileToPublicRSDTO() {
        // When
        final ProfileODTO profileODTO = null;

        // Then
        final var result = mapper.toPublicRSDTO(profileODTO);

        // Verify
        assertNull(result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER_MAPPER] - Test successful mapping from MetadataODTO to MetadataRSDTO.")
    void testMetadataToRSDTO() {
        // When
        final var metadataODTO = mock(MetadataODTO.class);

        // Then
        final var result = mapper.toRSDTO(metadataODTO);

        // Verify
        assertNotNull(result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER_MAPPER] - Test unsuccessful mapping from MetadataODTO to MetadataRSDTO.")
    void testWrongMetadataToRSDTO() {
        // When
        final MetadataODTO metadataODTO = null;

        // Then
        final var result = mapper.toRSDTO(metadataODTO);

        // Verify
        assertNull(result);
    }
}
