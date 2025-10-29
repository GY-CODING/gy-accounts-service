package org.gycoding.accounts.infrastructure.external.database.repository.impl;

import org.gycoding.accounts.domain.exceptions.AccountsError;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.infrastructure.external.database.mapper.MetadataDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.model.metadata.MetadataEntity;
import org.gycoding.accounts.infrastructure.external.database.repository.MetadataMongoRepository;
import org.gycoding.quasar.exceptions.model.DatabaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class MetadataDatabaseImplTest {
    @Mock
    private MetadataMongoRepository repository;

    @Mock
    private MetadataDatabaseMapper mapper;

    @InjectMocks
    private MetadataDatabaseImpl database;

    @Test
    @DisplayName("[METADATA_DATABASE] - Test successful save of Metadata.")
    void testSaveMetadata() {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var metadataEntity = mock(MetadataEntity.class);

        when(mapper.toEntity(metadataMO)).thenReturn(metadataEntity);
        when(repository.save(metadataEntity)).thenReturn(metadataEntity);
        when(mapper.toMO(metadataEntity)).thenReturn(metadataMO);

        // Then
        final var result = database.save(metadataMO);

        // Verify
        verify(mapper).toEntity(metadataMO);
        verify(repository).save(metadataEntity);
        verify(mapper).toMO(metadataEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(metadataMO, result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test successful update of Metadata.")
    void testUpdateMetadata() throws DatabaseException {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var metadataEntity = mock(MetadataEntity.class);
        final var metadataUpdatedEntity = mock(MetadataEntity.class);

        when(repository.findByUserId(metadataMO.userId())).thenReturn(Optional.of(metadataEntity));
        when(mapper.toUpdatedEntity(metadataEntity, metadataMO)).thenReturn(metadataUpdatedEntity);
        when(repository.save(metadataUpdatedEntity)).thenReturn(metadataUpdatedEntity);
        when(mapper.toMO(metadataUpdatedEntity)).thenReturn(metadataMO);

        // Then
        final var result = database.update(metadataMO);

        // Verify
        verify(repository).findByUserId(metadataMO.userId());
        verify(mapper).toUpdatedEntity(metadataEntity, metadataMO);
        verify(repository).save(metadataUpdatedEntity);
        verify(mapper).toMO(metadataUpdatedEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(metadataMO, result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test unsuccessful update of Metadata due to a non-existent Metadata on database.")
    void testWrongUpdateMetadata() {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var expectedException = new DatabaseException(AccountsError.RESOURCE_NOT_FOUND);

        when(repository.findByUserId(metadataMO.userId())).thenReturn(Optional.empty());

        // Then
        final var error = assertThrows(
                DatabaseException.class,
                () -> database.update(metadataMO)
        );

        // Verify
        verify(repository).findByUserId(metadataMO.userId());
        verifyNoMoreInteractions(repository);

        assertEquals(expectedException.getCode(), error.getCode());
        assertEquals(expectedException.getStatus(), error.getStatus());
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test successful refresh of Metadata.")
    void testRefreshMetadata() throws DatabaseException {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var metadataEntity = mock(MetadataEntity.class);
        final var metadataRefreshedEntity = mock(MetadataEntity.class);

        when(repository.findByUserId(metadataMO.userId())).thenReturn(Optional.of(metadataEntity));
        when(mapper.toEntity(metadataMO)).thenReturn(metadataEntity);
        when(mapper.toMO(metadataEntity)).thenReturn(metadataMO);
        when(mapper.toRefreshedEntity(metadataEntity, metadataMO, metadataEntity.getMongoId())).thenReturn(metadataRefreshedEntity);
        when(repository.save(metadataRefreshedEntity)).thenReturn(metadataRefreshedEntity);
        when(mapper.toMO(metadataRefreshedEntity)).thenReturn(metadataMO);

        // Then
        final var result = database.refresh(metadataMO);

        // Verify
        verify(repository).findByUserId(metadataMO.userId());
        verify(mapper).toEntity(metadataMO);
        verify(mapper).toMO(metadataEntity);
        verify(mapper).toRefreshedEntity(metadataEntity, metadataMO, metadataEntity.getMongoId());
        verify(repository).save(metadataRefreshedEntity);
        verify(mapper).toMO(metadataRefreshedEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(metadataMO, result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test unsuccessful refresh of Metadata due to a non-existent Metadata on database.")
    void testWrongRefreshMetadata() {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var expectedException = new DatabaseException(AccountsError.RESOURCE_NOT_FOUND);

        when(repository.findByUserId(metadataMO.userId())).thenReturn(Optional.empty());

        // Then
        final var error = assertThrows(
                DatabaseException.class,
                () -> database.refresh(metadataMO)
        );

        // Verify
        verify(repository).findByUserId(metadataMO.userId());
        verifyNoMoreInteractions(repository);

        assertEquals(expectedException.getCode(), error.getCode());
        assertEquals(expectedException.getStatus(), error.getStatus());
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test successful retrieval of Metadata using UserID.")
    void testGetMetadataWithUserID() {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var metadataEntity = mock(MetadataEntity.class);

        when(repository.findByUserId(metadataMO.userId())).thenReturn(Optional.of(metadataEntity));
        when(mapper.toMO(metadataEntity)).thenReturn(metadataMO);

        // Then
        final var result = database.get(metadataMO.userId());

        // Verify
        verify(repository).findByUserId(metadataMO.userId());
        verify(mapper).toMO(metadataEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(Optional.of(metadataMO), result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test unsuccessful retrieval of Metadata using UserID.")
    void testWrongGetMetadataWithUserID() {
        // When
        final var metadataMO = mock(MetadataMO.class);

        when(repository.findByUserId(metadataMO.userId())).thenReturn(Optional.empty());

        // Then
        final var result = database.get(metadataMO.userId());

        // Verify
        verify(repository).findByUserId(metadataMO.userId());
        verifyNoMoreInteractions(repository);

        assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test successful retrieval of Metadata using ProfileID.")
    void testGetMetadataWithProfileID() {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var profileID = mock(UUID.class);
        final var metadataEntity = mock(MetadataEntity.class);

        when(repository.findByProfileId(profileID.toString())).thenReturn(Optional.of(metadataEntity));
        when(mapper.toMO(metadataEntity)).thenReturn(metadataMO);

        // Then
        final var result = database.get(profileID);

        // Verify
        verify(repository).findByProfileId(profileID.toString());
        verify(mapper).toMO(metadataEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(Optional.of(metadataMO), result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test unsuccessful retrieval of Metadata using ProfileID.")
    void testWrongGetMetadataWithProfileID() {
        // When
        final var profileID = mock(UUID.class);

        when(repository.findByProfileId(profileID.toString())).thenReturn(Optional.empty());

        // Then
        final var result = database.get(profileID);

        // Verify
        verify(repository).findByProfileId(profileID.toString());
        verifyNoMoreInteractions(repository);

        assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test successful retrieval of Metadata using API Key.")
    void testGetMetadataWithApiKey () {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var apiKey = "mock-api-key";
        final var metadataEntity = mock(MetadataEntity.class);

        when(repository.findByProfileApiKey(apiKey)).thenReturn(Optional.of(metadataEntity));
        when(mapper.toMO(metadataEntity)).thenReturn(metadataMO);

        // Then
        final var result = database.getByApiKey(apiKey);

        // Verify
        verify(repository).findByProfileApiKey(apiKey);
        verify(mapper).toMO(metadataEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(Optional.of(metadataMO), result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test unsuccessful retrieval of Metadata using API Key.")
    void testWrongGetMetadataWithApiKey() {
        // When
        final var apiKey = "mock-api-key";

        when(repository.findByProfileApiKey(apiKey)).thenReturn(Optional.empty());

        // Then
        final var result = database.getByApiKey(apiKey);

        // Verify
        verify(repository).findByProfileApiKey(apiKey);
        verifyNoMoreInteractions(repository);

        assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE] - Test successful list of Metadata.")
    void testListMetadata () {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var query = "mock-query";
        final var metadataEntity = mock(MetadataEntity.class);

        when(repository.findAllByProfileUsername(query)).thenReturn(List.of(metadataEntity));
        when(mapper.toMO(metadataEntity)).thenReturn(metadataMO);

        // Then
        final var result = database.list(query);

        // Verify
        verify(repository).findAllByProfileUsername(query);
        verify(mapper).toMO(metadataEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(List.of(metadataMO), result);
    }
}