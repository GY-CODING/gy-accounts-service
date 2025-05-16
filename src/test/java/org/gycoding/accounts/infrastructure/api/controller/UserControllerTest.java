package org.gycoding.accounts.infrastructure.api.controller;

import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.service.user.UserService;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.ProfileRSDTO;
import org.gycoding.accounts.infrastructure.api.mapper.UserControllerMapper;
import org.gycoding.accounts.stub.UserStubs;
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
public class UserControllerTest {
    @Mock
    private UserService service;

    @Mock
    private UserControllerMapper mapper;

    @InjectMocks
    private UserController controller;

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful retrieve of profile case.")
    void testGetProfile() throws APIException {
        // When
        final var profileODTO = mock(ProfileODTO.class);
        final var profileRSDTO = mock(ProfileRSDTO.class);

        when(mapper.toRSDTO(profileODTO)).thenReturn(profileRSDTO);
        when(service.getProfile(UserStubs.AUTH0_USER_ID)).thenReturn(profileODTO);

        // Then
        final var result = controller.getProfile(UserStubs.AUTH0_USER_ID);

        // Verify
        verify(mapper).toRSDTO(profileODTO);
        verify(service).getProfile(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(mapper, service);

        assertEquals(profileRSDTO, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful retrieve of profile case.")
    void testWrongLogin() throws APIException {
        // When
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
        );

        when(service.getProfile(UserStubs.AUTH0_USER_ID)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.getProfile(UserStubs.AUTH0_USER_ID)
        );

        // Verify
        verify(service).getProfile(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }
}
