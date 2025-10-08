package org.gycoding.accounts.infrastructure.api.mapper;

import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.auth.UserRQDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AuthControllerMapperTest {
    @InjectMocks
    private AuthControllerMapperImpl mapper;

    @Test
    @DisplayName("[AUTH_CONTROLLER_MAPPER] - Test successful mapping from UserRQDTO to UserIDTO.")
    void testUserToIDTO() {
        // When
        final var userRQDTO = mock(UserRQDTO.class);
        final var userIDTO = mock(UserIDTO.class);

        // Then
        final var result = mapper.toIDTO(userRQDTO);

        // Verify
        assertEquals(userIDTO.username(), result.username());
        assertEquals(userIDTO.email(), result.email());
        assertEquals(userIDTO.password(), result.password());
    }

    @Test
    @DisplayName("[AUTH_CONTROLLER_MAPPER] - Test unsuccessful mapping from UserRQDTO to UserIDTO.")
    void testWrongUserToIDTO() {
        // Then
        final var result = mapper.toIDTO(null);

        // Verify
        assertNull(result);
    }
}
