package org.gycoding.accounts.infrastructure.api.controller;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.accounts.application.service.auth.AuthService;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.api.dto.in.auth.UserRQDTO;
import org.gycoding.accounts.infrastructure.api.mapper.AuthControllerMapper;
import org.gycoding.exceptions.model.APIException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private AuthService service;

    @Mock
    private AuthControllerMapper mapper;

    @InjectMocks
    private AuthController controller;

    @Test
    @DisplayName("[AUTH_CONTROLLER] - Test successful login case.")
    void testLogin() throws APIException {
        // When
        final var userRQDTO = mock(UserRQDTO.class);
        final var userIDTO = mock(UserIDTO.class);
        final var userCredentials = mock(TokenHolder.class);

        when(mapper.toIDTO(userRQDTO)).thenReturn(userIDTO);
        when(service.login(userIDTO)).thenReturn(userCredentials);

        // Then
        final var result = controller.login(userRQDTO);

        // Verify
        verify(mapper).toIDTO(userRQDTO);
        verify(service).login(userIDTO);
        verifyNoMoreInteractions(mapper, service);

        assertEquals(userCredentials, result.getBody());
    }

    @Test
    @DisplayName("[AUTH_CONTROLLER] - Test unsuccessful login case.")
    void testWrongLogin() throws APIException {
        // When
        final var userRQDTO = mock(UserRQDTO.class);
        final var userIDTO = mock(UserIDTO.class);
        final var expectedException = new APIException(
                AccountsAPIError.INVALID_LOGIN.getCode(),
                AccountsAPIError.INVALID_LOGIN.getMessage(),
                AccountsAPIError.INVALID_LOGIN.getStatus()
        );

        when(mapper.toIDTO(userRQDTO)).thenReturn(userIDTO);
        when(service.login(userIDTO)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.login(userRQDTO)
        );

        // Verify
        verify(mapper).toIDTO(userRQDTO);
        verify(service).login(userIDTO);
        verifyNoMoreInteractions(mapper, service);

        assertEquals(expectedException, result);
    }

    @Test
    @DisplayName("[AUTH_CONTROLLER] - Test successful sign up case.")
    void testSignUp() throws APIException {
        // When
        final var userRQDTO = mock(UserRQDTO.class);
        final var userIDTO = mock(UserIDTO.class);
        final var userCreated = mock(CreatedUser.class);

        when(mapper.toIDTO(userRQDTO)).thenReturn(userIDTO);
        when(service.signUp(userIDTO)).thenReturn(userCreated);

        // Then
        final var result = controller.signUp(userRQDTO);

        // Verify
        verify(mapper).toIDTO(userRQDTO);
        verify(service).signUp(userIDTO);
        verifyNoMoreInteractions(mapper, service);

        assertEquals(userCreated, result.getBody());
    }

    @Test
    @DisplayName("[AUTH_CONTROLLER] - Test unsuccessful sign up case.")
    void testWrongSignUp() throws APIException {
        // When
        final var userRQDTO = mock(UserRQDTO.class);
        final var userIDTO = mock(UserIDTO.class);
        final var expectedException = new APIException(
                AccountsAPIError.INVALID_SIGNUP.getCode(),
                AccountsAPIError.INVALID_SIGNUP.getMessage(),
                AccountsAPIError.INVALID_SIGNUP.getStatus()
        );

        when(mapper.toIDTO(userRQDTO)).thenReturn(userIDTO);
        when(service.signUp(userIDTO)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.signUp(userRQDTO)
        );

        // Verify
        verify(mapper).toIDTO(userRQDTO);
        verify(service).signUp(userIDTO);
        verifyNoMoreInteractions(mapper, service);

        assertEquals(expectedException, result);
    }
}
