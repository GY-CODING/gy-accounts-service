package org.gycoding.accounts.application.service.auth;

import org.bson.types.ObjectId;
import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.domain.entities.database.EntityUsername;
import org.gycoding.exceptions.model.APIException;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataRepository {
    EntityUsername saveUsername(String userId, String username) throws APIException;
    EntityPicture savePicture(MultipartFile picture) throws APIException;
    EntityPicture getPicture(String pictureName) throws APIException;
    void setupMetadata(String token) throws APIException;
}