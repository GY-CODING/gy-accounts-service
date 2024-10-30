package org.gycoding.accounts.application.service.auth;

import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.domain.entities.database.EntityUsername;
import org.gycoding.exceptions.model.APIException;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataRepository {
    EntityUsername saveUsername(String userId, String username) throws APIException;
    EntityPicture savePicture(String userId) throws APIException;
    EntityPicture savePicture(String userId, MultipartFile picture) throws APIException;
    EntityPicture getPicture(String userId) throws APIException;
    void setupMetadata(String token) throws APIException;
}