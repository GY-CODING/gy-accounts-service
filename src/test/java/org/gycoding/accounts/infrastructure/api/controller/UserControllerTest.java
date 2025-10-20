package org.gycoding.accounts.infrastructure.api.controller;

import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.service.user.UserService;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.ProfileRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.TransformIDRQDTO;
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
        controller.updatePicture(updatedPictureURL, UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).updatePicture(eq(UserStubs.AUTH0_USER_ID), any(MockMultipartFile.class));
        verifyNoMoreInteractions(service);
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

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful retrieve of user's metadata case.")
    void testGetMetadata() throws APIException {
        // When
        final var metadata = mock(MetadataODTO.class);

        when(service.getMetadata(UserStubs.AUTH0_USER_ID)).thenReturn(metadata);

        // Then
        final var result = controller.getMetadata(UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).getMetadata(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(metadata, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful retrieve of user's metadata case.")
    void testWrongGetMetadata() throws APIException {
        // When
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(service.getMetadata(UserStubs.AUTH0_USER_ID)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.getMetadata(UserStubs.AUTH0_USER_ID)
        );

        // Verify
        verify(service).getMetadata(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful sync of user's metadata case.")
    void testSyncMetadata() throws APIException {
        // When
        final var metadata = mock(MetadataODTO.class);

        when(service.syncMetadata(UserStubs.AUTH0_USER_ID)).thenReturn(metadata);

        // Then
        final var result = controller.syncMetadata(UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).syncMetadata(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(metadata, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful sync of user's metadata case.")
    void testWrongSyncMetadata() throws APIException {
        // When
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(service.syncMetadata(UserStubs.AUTH0_USER_ID)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.syncMetadata(UserStubs.AUTH0_USER_ID)
        );

        // Verify
        verify(service).syncMetadata(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful refresh of an API Key case.")
    void testRefreshAPIKey() throws APIException {
        // When
        when(service.refreshApiKey(UserStubs.AUTH0_USER_ID)).thenReturn(UserStubs.API_KEY.toString());

        // Then
        final var result = controller.refreshApiKey(UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).refreshApiKey(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(UserStubs.API_KEY.toString(), result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful refresh of API Key case.")
    void testWrongRefreshAPIKey() throws APIException {
        // When
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(service.refreshApiKey(UserStubs.AUTH0_USER_ID)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.refreshApiKey(UserStubs.AUTH0_USER_ID)
        );

        // Verify
        verify(service).refreshApiKey(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful retrieve of User ID given a Profile ID case.")
    void testGetUserID() throws APIException {
        // When
        final var transformID = TransformIDRQDTO.builder()
                .profileId(UserStubs.PROFILE_ID.toString())
                .build();

        when(service.transform(UserStubs.PROFILE_ID)).thenReturn(UserStubs.AUTH0_USER_ID);

        // Then
        final var result = controller.getUserId(transformID);

        // Verify
        verify(service).transform(UserStubs.PROFILE_ID);
        verifyNoMoreInteractions(service);

        assertEquals(UserStubs.AUTH0_USER_ID, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful retrieve of User ID given a Profile ID case.")
    void testWrongGetUserID() throws APIException {
        // When
        final var transformID = TransformIDRQDTO.builder()
                .profileId(UserStubs.PROFILE_ID.toString())
                .build();
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(service.transform(UserStubs.PROFILE_ID)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.getUserId(transformID)
        );

        // Verify
        verify(service).transform(UserStubs.PROFILE_ID);
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful retrieve of Profile ID given a User ID case.")
    void testGetProfileID() throws APIException {
        // When
        final var transformID = TransformIDRQDTO.builder()
                .userId(UserStubs.AUTH0_USER_ID)
                .build();

        when(service.transform(UserStubs.AUTH0_USER_ID)).thenReturn(UserStubs.PROFILE_ID);

        // Then
        final var result = controller.getProfileId(transformID);

        // Verify
        verify(service).transform(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(UserStubs.PROFILE_ID, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test unsuccessful retrieve of Profile ID given a User ID case.")
    void testWrongGetProfileID() throws APIException {
        // When
        final var transformID = TransformIDRQDTO.builder()
                .userId(UserStubs.AUTH0_USER_ID)
                .build();
        final var expectedException = new APIException(
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
        );

        when(service.transform(UserStubs.AUTH0_USER_ID)).thenThrow(expectedException);

        // Then
        final var result = assertThrows(
                APIException.class,
                () -> controller.getProfileId(transformID)
        );

        // Verify
        verify(service).transform(UserStubs.AUTH0_USER_ID);
        verifyNoMoreInteractions(service);

        assertEquals(expectedException, result);
    }
}
