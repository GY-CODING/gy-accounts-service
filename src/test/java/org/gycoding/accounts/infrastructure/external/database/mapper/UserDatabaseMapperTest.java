package org.gycoding.accounts.infrastructure.external.database.mapper;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserDatabaseMapperTest {
    @InjectMocks
    private UserDatabaseMapperImpl mapper;

    @Test
    @DisplayName("[USER_DATABASE_MAPPER] - Test successful mapping from PictureEntity to PictureMO.")
    void testPictureToMO() {
        // When
        final var pictureEntity = mock(PictureEntity.class);
        final var pictureMO = mock(PictureMO.class);

        // Then
        final var result = mapper.toMO(pictureEntity);

        // Verify
        assertEquals(pictureMO.name(), result.name());
        assertEquals(pictureMO.contentType(), result.contentType());
        assertEquals(pictureMO.picture(), result.picture());
    }

    @Test
    @DisplayName("[USER_DATABASE_MAPPER] - Test unsuccessful mapping from PictureEntity to PictureMO.")
    void testWrongPictureToMO() {
        // Then
        final var result = mapper.toMO(null);

        // Verify
        assertNull(result);
    }

    @Test
    @DisplayName("[USER_DATABASE_MAPPER] - Test successful mapping from PictureMO to PictureEntity.")
    void testPictureToEntity() {
        // When
        final var pictureMO = mock(PictureMO.class);
        final var pictureEntity = mock(PictureEntity.class);

        // Then
        final var result = mapper.toEntity(pictureMO);

        // Verify
        assertEquals(pictureEntity.getName(), result.getName());
        assertEquals(pictureEntity.getContentType(), result.getContentType());
        assertEquals(pictureEntity.getPicture(), result.getPicture());
    }

    @Test
    @DisplayName("[USER_DATABASE_MAPPER] - Test unsuccessful mapping from PictureEntity to PictureMO.")
    void testWrongPictureToEntity() {
        // Then
        final var result = mapper.toEntity(null);

        // Verify
        assertNull(result);
    }
}
