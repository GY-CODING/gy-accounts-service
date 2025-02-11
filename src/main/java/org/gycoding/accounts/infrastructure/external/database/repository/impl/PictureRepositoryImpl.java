package org.gycoding.accounts.infrastructure.external.database.repository.impl;

import kong.unirest.json.JSONObject;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.repository.PictureRepository;
import org.gycoding.accounts.infrastructure.external.database.mapper.UserDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.repository.PictureMongoRepository;
import org.gycoding.accounts.shared.utils.logger.LogDTO;
import org.gycoding.accounts.shared.utils.logger.LogLevel;
import org.gycoding.accounts.shared.utils.logger.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PictureRepositoryImpl implements PictureRepository {
    private final PictureMongoRepository repository;

    private final UserDatabaseMapper mapper;

    @Override
    public PictureMO save(PictureMO picture) {
        final var originalPicture = repository.findByName(picture.name());

        if(originalPicture == null) {
            return mapper.toMO(repository.save(mapper.toEntity(picture)));
        }

        originalPicture.setPicture(picture.picture());

        return mapper.toMO(repository.save(originalPicture));
    }

    @Override
    public PictureMO get(String pictureName) {
        return mapper.toMO(repository.findByName(pictureName));
    }
}
