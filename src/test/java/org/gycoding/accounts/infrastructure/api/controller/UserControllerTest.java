package org.gycoding.accounts.infrastructure.api.controller;

import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.service.user.UserService;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.ProfileRQDTO;
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
import org.springframework.mock.web.MockMultipartFile;

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

        when(service.getProfile(UserStubs.AUTH0_USER_ID)).thenReturn(profileODTO);
        when(mapper.toRSDTO(profileODTO)).thenReturn(profileRSDTO);

        // Then
        final var result = controller.getProfile(UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).getProfile(UserStubs.AUTH0_USER_ID);
        verify(mapper).toRSDTO(profileODTO);
        verifyNoMoreInteractions(mapper, service);

        assertEquals(profileRSDTO, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful retrieve of profile case.")
    void testWrongGetProfile() throws APIException {
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

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful update of profile case.")
    void testUpdateProfile() throws APIException {
        // When
        final var profileRQDTO = mock(ProfileRQDTO.class);
        final var profileIDTO = mock(ProfileIDTO.class);
        final var profileODTO = mock(ProfileODTO.class);
        final var profileRSDTO = mock(ProfileRSDTO.class);

        when(mapper.toIDTO(profileRQDTO)).thenReturn(profileIDTO);
        when(service.updateProfile(UserStubs.AUTH0_USER_ID, profileIDTO)).thenReturn(profileODTO);
        when(mapper.toRSDTO(profileODTO)).thenReturn(profileRSDTO);

        // Then
        final var result = controller.updateProfile(profileRQDTO, UserStubs.AUTH0_USER_ID);

        // Verify
        verify(mapper).toIDTO(profileRQDTO);
        verify(service).updateProfile(UserStubs.AUTH0_USER_ID, profileIDTO);
        verify(mapper).toRSDTO(profileODTO);
        verifyNoMoreInteractions(mapper, service);

        assertEquals(profileRSDTO, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful update of profile case.")
    void testWrongUpdateProfile() throws APIException {
        // When
        final var profileRQDTO = mock(ProfileRQDTO.class);
        final var profileIDTO = mock(ProfileIDTO.class);
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(mapper.toIDTO(profileRQDTO)).thenReturn(profileIDTO);
        when(service.updateProfile(UserStubs.AUTH0_USER_ID, profileIDTO)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.updateProfile(profileRQDTO, UserStubs.AUTH0_USER_ID)
        );

        // Verify
        verify(mapper).toIDTO(profileRQDTO);
        verify(service).updateProfile(UserStubs.AUTH0_USER_ID, profileIDTO);
        verifyNoMoreInteractions(mapper, service);

        assertEquals(expectedException, result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful update of username case.")
    void testUpdateUsername() throws APIException {
        // When
        final var updatedUsername = "New Username";

        when(service.updateUsername(UserStubs.AUTH0_USER_ID, updatedUsername)).thenReturn(updatedUsername);

        // Then
        final var result = controller.updateUsername(updatedUsername, UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).updateUsername(UserStubs.AUTH0_USER_ID, updatedUsername);
        verifyNoMoreInteractions(service);

        assertEquals(updatedUsername, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful update of username case.")
    void testWrongUpdateUsername() throws APIException {
        // When
        final var updatedUsername = "New Username";
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(service.updateUsername(UserStubs.AUTH0_USER_ID, updatedUsername)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.updateUsername(updatedUsername, UserStubs.AUTH0_USER_ID)
        );

        // Verify
        verify(service).updateUsername(UserStubs.AUTH0_USER_ID, updatedUsername);
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful update of picture case.")
    void testUpdatePicture() throws APIException {
        // When
        final var updatedPictureURL = "https://picsum.photos/200";
        final var pictureODTO = mock(PictureODTO.class);

        when(service.updatePicture(eq(UserStubs.AUTH0_USER_ID), any(MockMultipartFile.class))).thenReturn(pictureODTO);

        // Then
        final var result = controller.updatePicture(updatedPictureURL, UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).updatePicture(eq(UserStubs.AUTH0_USER_ID), any(MockMultipartFile.class));
        verifyNoMoreInteractions(service);

        assertEquals(pictureODTO.toString(), result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful update of picture case.")
    void testWrongUpdatePicture() throws APIException {
        // When
        final var updatedPictureURL = "https://picsum.photos/200";
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(service.updatePicture(eq(UserStubs.AUTH0_USER_ID), any(MockMultipartFile.class))).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.updatePicture(updatedPictureURL, UserStubs.AUTH0_USER_ID)
        );

        // Verify
        verify(service).updatePicture(eq(UserStubs.AUTH0_USER_ID), any(MockMultipartFile.class));
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful update of phone number case.")
    void testUpdatePhoneNumber() throws APIException {
        // When
        final var updatedPhoneNumber = "+34 987654321";

        when(service.updatePhoneNumber(UserStubs.AUTH0_USER_ID, updatedPhoneNumber)).thenReturn(updatedPhoneNumber);

        // Then
        final var result = controller.updatePhoneNumber(updatedPhoneNumber, UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).updatePhoneNumber(UserStubs.AUTH0_USER_ID, updatedPhoneNumber);
        verifyNoMoreInteractions(service);

        assertEquals(updatedPhoneNumber, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful update of phone number case.")
    void testWrongUpdatePhoneNumber() throws APIException {
        // When
        final var updatedPhoneNumber = "+34 987654321";
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(service.updatePhoneNumber(UserStubs.AUTH0_USER_ID, updatedPhoneNumber)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.updatePhoneNumber(updatedPhoneNumber, UserStubs.AUTH0_USER_ID)
        );

        // Verify
        verify(service).updatePhoneNumber(UserStubs.AUTH0_USER_ID, updatedPhoneNumber);
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }
}
