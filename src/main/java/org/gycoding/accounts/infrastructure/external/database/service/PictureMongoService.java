package org.gycoding.accounts.infrastructure.external.database.service;

import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.infrastructure.external.database.repository.PictureMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureMongoService {
    @Autowired
    private PictureMongoRepository pictureMongoRepository;

    public EntityPicture save(EntityPicture picture) {
        final var originalPicture = this.getPicture(picture.getName());

        originalPicture.setPicture(picture.getPicture());

        return pictureMongoRepository.save(originalPicture);
    }

    public EntityPicture getPicture(String pictureName) {
        return pictureMongoRepository.findByName(pictureName);
    }
}
