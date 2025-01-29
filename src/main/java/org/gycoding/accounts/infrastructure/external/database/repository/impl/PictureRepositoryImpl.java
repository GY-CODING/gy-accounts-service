package org.gycoding.accounts.infrastructure.external.database.repository.impl;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.repository.PictureRepository;
import org.gycoding.accounts.infrastructure.external.database.mapper.UserDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.repository.PictureMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PictureRepositoryImpl implements PictureRepository {
    @Autowired
    private PictureMongoRepository repository;

    @Autowired
    private UserDatabaseMapper mapper;

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
