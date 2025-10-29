package org.gycoding.accounts.infrastructure.external.database.repository.impl;

import lombok.AllArgsConstructor;
import org.gycoding.accounts.domain.exceptions.AccountsError;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.repository.MetadataRepository;
import org.gycoding.accounts.infrastructure.external.database.mapper.MetadataDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.repository.MetadataMongoRepository;
import org.gycoding.quasar.exceptions.model.DatabaseException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MetadataDatabaseImpl implements MetadataRepository {
    private final MetadataMongoRepository repository;
    private final MetadataDatabaseMapper mapper;

    @Override
    public MetadataMO save(MetadataMO metadata) {
        return mapper.toMO(repository.save(mapper.toEntity(metadata)));
    }

    @Override
    public MetadataMO update(MetadataMO metadata) throws DatabaseException {
        final var persistedMetadata = repository.findByUserId(metadata.userId())
                .orElseThrow(() -> new DatabaseException(AccountsError.RESOURCE_NOT_FOUND));

        return mapper.toMO(repository.save(mapper.toUpdatedEntity(persistedMetadata, metadata)));
    }

    @Override
    public MetadataMO refresh(MetadataMO metadata) throws DatabaseException {
        final var persistedMetadata = repository.findByUserId(metadata.userId())
                .orElseThrow(() -> new DatabaseException(AccountsError.RESOURCE_NOT_FOUND));

        return mapper.toMO(repository.save(mapper.toRefreshedEntity(mapper.toEntity(metadata), mapper.toMO(persistedMetadata), persistedMetadata.getMongoId())));
    }

    @Override
    public Optional<MetadataMO> get(String userId) {
        return repository.findByUserId(userId)
                .map(mapper::toMO);
    }

    @Override
    public Optional<MetadataMO> get(UUID profileId) {
        return repository.findByProfileId(profileId.toString())
                .map(mapper::toMO);
    }

    @Override
    public Optional<MetadataMO> getByApiKey(String apiKey) {
        return repository.findByProfileApiKey(apiKey)
                .map(mapper::toMO);
    }

    @Override
    public List<MetadataMO> list(String query) {
        return repository.findAllByProfileUsername(query).stream()
                .map(mapper::toMO)
                .toList();
    }
}
