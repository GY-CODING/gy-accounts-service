package org.gycoding.accounts.infrastructure.external.database.repository.impl;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.infrastructure.external.database.mapper.UserDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PictureRepositoryImpl {
    private PictureRepository repository;

    @Qualifier("userDatabaseMapperImpl")
    @Autowired
    private UserDatabaseMapper mapper;

    public PictureMO save(PictureMO picture) {
        final var originalPicture = repository.findByName(picture.name());

        if(originalPicture == null) {
            return mapper.toMO(repository.save(mapper.toEntity(picture)));
        }

        originalPicture.setPicture(picture.picture());

        return mapper.toMO(repository.save(originalPicture));
    }

    public PictureMO getPicture(String pictureName) {
        return mapper.toMO(repository.findByName(pictureName));
    }
}
