package org.gycoding.accounts.infrastructure.external.database.service;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.infrastructure.external.database.repository.PictureMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureMongoService {
    @Autowired
    private PictureMongoRepository pictureMongoRepository;

    public EntityPicture save(EntityPicture picture) throws IOException {
        return pictureMongoRepository.insert(picture);
    }

    public EntityPicture getPicture(String pictureName) {
        return pictureMongoRepository.findByName(pictureName);
    }
}
