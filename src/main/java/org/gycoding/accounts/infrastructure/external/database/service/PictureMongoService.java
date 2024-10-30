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

    public EntityPicture save(String userId, MultipartFile picture) throws IOException {
        userId = userId.replace("google-oauth2|", "");
        userId = userId.replace("auth0|", "");

        return pictureMongoRepository.insert(
                EntityPicture.builder()
                        .name(userId + "-pfp")
                        .contentType(picture.getContentType())
                        .picture(new Binary(BsonBinarySubType.BINARY, picture.getBytes()))
                .build()
        );
    }

    public EntityPicture getPicture(String userId) {
        return pictureMongoRepository.findByName(userId + "-pfp");
    }
}
