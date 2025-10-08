package org.gycoding.accounts.infrastructure.external.database.repository.impl;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.infrastructure.external.database.mapper.UserDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.gycoding.accounts.infrastructure.external.database.model.metadata.MetadataEntity;
import org.gycoding.accounts.infrastructure.external.database.repository.PictureMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PictureDatabaseImplTest {
    @Mock
    private PictureMongoRepository repository;

    @Mock
    private UserDatabaseMapper mapper;

    @InjectMocks
    private PictureDatabaseImpl database;

    @Test
    @DisplayName("[PICTURE_DATABASE] - Test successful save of a Picture.")
    void testSavePicture() {
        // When
        final var pictureMO = mock(PictureMO.class);
        final var pictureEntity = mock(PictureEntity.class);

        when(repository.findByName(pictureMO.name())).thenReturn(null);
        when(mapper.toEntity(pictureMO)).thenReturn(pictureEntity);
        when(repository.save(pictureEntity)).thenReturn(pictureEntity);
        when(mapper.toMO(pictureEntity)).thenReturn(pictureMO);

        // Then
        final var result = database.save(pictureMO);

        // Verify
        verify(repository).findByName(pictureMO.name());
        verify(mapper).toEntity(pictureMO);
        verify(repository).save(pictureEntity);
        verify(mapper).toMO(pictureEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(pictureMO, result);
    }

    @Test
    @DisplayName("[PICTURE_DATABASE] - Test successful update of an existing Picture.")
    void testUpdatePicture() {
        // When
        final var pictureMO = mock(PictureMO.class);
        final var pictureEntity = mock(PictureEntity.class);

        when(repository.findByName(pictureMO.name())).thenReturn(pictureEntity);
        when(repository.save(pictureEntity)).thenReturn(pictureEntity);
        when(mapper.toMO(pictureEntity)).thenReturn(pictureMO);

        // Then
        final var result = database.save(pictureMO);

        // Verify
        verify(repository).findByName(pictureMO.name());
        verify(repository).save(pictureEntity);
        verify(mapper).toMO(pictureEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(pictureMO, result);
    }

    @Test
    @DisplayName("[PICTURE_DATABASE] - Test successful retrieval of an existing Picture.")
    void testGetPicture() {
        // When
        final var pictureMO = mock(PictureMO.class);
        final var pictureEntity = mock(PictureEntity.class);

        when(repository.findByName(pictureMO.name())).thenReturn(pictureEntity);
        when(mapper.toMO(pictureEntity)).thenReturn(pictureMO);

        // Then
        final var result = database.get(pictureMO.name());

        // Verify
        verify(repository).findByName(pictureMO.name());
        verify(mapper).toMO(pictureEntity);
        verifyNoMoreInteractions(repository, mapper);

        assertEquals(pictureMO, result);
    }
}
