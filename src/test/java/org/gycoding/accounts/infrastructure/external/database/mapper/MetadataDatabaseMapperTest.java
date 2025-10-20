package org.gycoding.accounts.infrastructure.external.database.mapper;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.gycoding.accounts.infrastructure.external.database.model.metadata.MetadataEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MetadataDatabaseMapperTest {
    @InjectMocks
    private MetadataDatabaseMapperImpl mapper;

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test successful mapping from MetadataEntity to MetadataMO.")
    void testMetadataToMO() {
        // When
        final var metadataEntity = mock(MetadataEntity.class);
        final var metadataMO = mock(MetadataMO.class);

        // Then
        final var result = mapper.toMO(metadataEntity);

        // Verify
        assertEquals(metadataMO.userId(), result.userId());
        assertEquals(metadataMO.profile(), result.profile());
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test unsuccessful mapping from MetadataEntity to MetadataMO.")
    void testWrongMetadataToMO() {
        // Then
        final var result = mapper.toMO(null);

        // Verify
        assertNull(result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test successful mapping from MetadataMO to MetadataEntity.")
    void testMetadataToEntity() {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var metadataEntity = mock(MetadataEntity.class);

        // Then
        final var result = mapper.toEntity(metadataMO);

        // Verify
        assertEquals(metadataEntity.getUserId(), result.getUserId());
        assertEquals(metadataEntity.getProfile(), result.getProfile());
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test unsuccessful mapping from MetadataMO to MetadataEntity.")
    void testWrongMetadataToEntity() {
        // Then
        final var result = mapper.toEntity(null);

        // Verify
        assertNull(result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test successful mapping from MetadataMO to updated MetadataEntity.")
    void testMetadataToUpdatedEntity() {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var metadataEntity = mock(MetadataEntity.class);
        final var metadataUpdatedEntity = mock(MetadataEntity.class);

        // Then
        final var result = mapper.toUpdatedEntity(metadataEntity, metadataMO);

        // Verify
        assertEquals(metadataUpdatedEntity.getUserId(), result.getUserId());
        assertEquals(metadataUpdatedEntity.getProfile(), result.getProfile());
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test unsuccessful mapping from MetadataMO to updated MetadataEntity.")
    void testWrongMetadataToUpdatedEntity() {
        // When
        final var metadataEntity = mock(MetadataEntity.class);

        // Then
        final var result = mapper.toUpdatedEntity(metadataEntity, null);

        // Verify
        assertEquals(metadataEntity, result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test successful mapping from MetadataMO to refreshed MetadataEntity.")
    void testMetadataToRefreshedEntity() {
        // When
        final var metadataMO = mock(MetadataMO.class);
        final var metadataEntity = mock(MetadataEntity.class);
        final var metadataRefreshedEntity = mock(MetadataEntity.class);
        final var mongoId = "mock-mongo-id";

        // Then
        final var result = mapper.toRefreshedEntity(metadataEntity, metadataMO, mongoId);

        // Verify
        assertEquals(metadataRefreshedEntity.getUserId(), result.getUserId());
        assertEquals(metadataRefreshedEntity.getProfile(), result.getProfile());
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test unsuccessful mapping from MetadataMO to refreshed MetadataEntity.")
    void testWrongMetadataToRefreshedEntity() {
        // When
        final var metadataEntity = mock(MetadataEntity.class);

        // Then
        final var result = mapper.toRefreshedEntity(metadataEntity, null, null);

        // Verify
        assertEquals(metadataEntity, result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test successful mapping from String to UUID.")
    void testStringToUUID() {
        // When
        final var id = "123e4567-e89b-12d3-a456-426614174000";

        // Then
        final var result = mapper.toUUID(id);

        // Verify
        assertEquals(UUID.fromString(id), result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test unsuccessful mapping from String to UUID.")
    void testWrongStringToUUID() {
        // Then
        final var result = mapper.toUUID(null);

        // Verify
        assertNull(result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test successful mapping from UUID to String.")
    void testUUIDToString() {
        // When
        final var id = mock(UUID.class);

        // Then
        final var result = mapper.toString(id);

        // Verify
        assertEquals(id.toString(), result);
    }

    @Test
    @DisplayName("[METADATA_DATABASE_MAPPER] - Test unsuccessful mapping from UUID to String.")
    void testWrongUUIDToString() {
        // Then
        final var result = mapper.toString(null);

        // Verify
        assertNull(result);
    }
}
