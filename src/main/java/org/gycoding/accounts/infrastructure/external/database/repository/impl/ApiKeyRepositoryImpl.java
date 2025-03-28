package org.gycoding.accounts.infrastructure.external.database.repository.impl;

import lombok.AllArgsConstructor;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.repository.ApiKeyRepository;
import org.gycoding.accounts.domain.repository.PictureRepository;
import org.gycoding.accounts.infrastructure.external.database.mapper.UserDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.model.ApiKeyEntity;
import org.gycoding.accounts.infrastructure.external.database.repository.ApiKeyMongoRepository;
import org.gycoding.accounts.infrastructure.external.database.repository.PictureMongoRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiKeyRepositoryImpl implements ApiKeyRepository {
    private final ApiKeyMongoRepository repository;

    private final UserDatabaseMapper mapper;

    @Override
    public String save(String userId, String newApiKey) {
        final var originalApiKey = repository.findByUserId(userId);

        if(originalApiKey == null) {
            repository.save(
                    ApiKeyEntity.builder()
                    .apiKey(newApiKey)
                    .userId(userId)
                    .build()
            );
        }

        originalApiKey.setApiKey(newApiKey);

        repository.save(originalApiKey);

        return newApiKey;
    }

    @Override
    public String getUserId(String apiKey) {
        return repository.findByApiKey(apiKey).getUserId();
    }
}
