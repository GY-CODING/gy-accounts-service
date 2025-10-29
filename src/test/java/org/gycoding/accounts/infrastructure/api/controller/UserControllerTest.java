package org.gycoding.accounts.infrastructure.api.controller;

import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.service.user.UserService;
import org.gycoding.accounts.domain.exceptions.AccountsError;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.ProfileRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.TransformIDRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.MetadataRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.ProfileRSDTO;
import org.gycoding.accounts.infrastructure.api.mapper.UserControllerMapper;
import org.gycoding.accounts.shared.MemoryMultipartFile;
import org.gycoding.accounts.stub.UserStubs;
import org.gycoding.quasar.exceptions.model.QuasarException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testGetProfile() throws QuasarException {
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
    @DisplayName("[USER_CONTROLLER] - Test successful update of profile case.")
    void testUpdateProfile() throws QuasarException {
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
    @DisplayName("[USER_CONTROLLER] - Test successful update of username case.")
    void testUpdateUsername() throws QuasarException {
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
    @DisplayName("[USER_CONTROLLER] - Test successful update of picture case.")
    void testUpdatePicture() throws QuasarException {
        // When
        final var updatedPictureURL = "https://picsum.photos/200";
        final var pictureODTO = mock(PictureODTO.class);

        when(service.updatePicture(eq(UserStubs.AUTH0_USER_ID), any(MemoryMultipartFile.class))).thenReturn(pictureODTO);

        // Then
        controller.updatePicture(updatedPictureURL, UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).updatePicture(eq(UserStubs.AUTH0_USER_ID), any(MemoryMultipartFile.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful update of phone number case.")
    void testUpdatePhoneNumber() throws QuasarException {
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
    @DisplayName("[USER_CONTROLLER] - Test successful retrieve of user's metadata case.")
    void testGetMetadata() throws QuasarException {
        // When
        final var metadataODTO = mock(MetadataODTO.class);
        final var metadataRSDTO = mock(MetadataRSDTO.class);

        when(service.getMetadata(UserStubs.AUTH0_USER_ID)).thenReturn(metadataODTO);
        when(mapper.toRSDTO(metadataODTO)).thenReturn(metadataRSDTO);

        // Then
        final var result = controller.getMetadata(UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).getMetadata(UserStubs.AUTH0_USER_ID);
        verify(mapper).toRSDTO(metadataODTO);
        verifyNoMoreInteractions(service, mapper);

        assertEquals(metadataRSDTO, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful sync of user's metadata case.")
    void testSyncMetadata() throws QuasarException {
        // When
        final var metadataODTO = mock(MetadataODTO.class);
        final var metadataRSDTO = mock(MetadataRSDTO.class);

        when(service.syncMetadata(UserStubs.AUTH0_USER_ID)).thenReturn(metadataODTO);
        when(mapper.toRSDTO(metadataODTO)).thenReturn(metadataRSDTO);

        // Then
        final var result = controller.syncMetadata(UserStubs.AUTH0_USER_ID);

        // Verify
        verify(service).syncMetadata(UserStubs.AUTH0_USER_ID);
        verify(mapper).toRSDTO(metadataODTO);
        verifyNoMoreInteractions(service, mapper);

        assertEquals(metadataRSDTO, result.getBody());
    }

    @Test
    @DisplayName("[USER_CONTROLLER] - Test successful refresh of an API Key case.")
    void testRefreshAPIKey() throws QuasarException {
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
    @DisplayName("[USER_CONTROLLER] - Test successful retrieve of User ID given a Profile ID case.")
    void testGetUserID() throws QuasarException {
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
    @DisplayName("[USER_CONTROLLER] - Test successful retrieve of Profile ID given a User ID case.")
    void testGetProfileID() throws QuasarException {
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

        assertEquals(UserStubs.PROFILE_ID.toString(), result.getBody());
    }
}
