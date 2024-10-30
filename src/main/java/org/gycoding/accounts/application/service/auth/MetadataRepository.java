package org.gycoding.accounts.application.service.auth;

import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.domain.entities.database.EntityUsername;
import org.gycoding.accounts.domain.entities.metadata.UserMetadata;
import org.gycoding.exceptions.model.APIException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface MetadataRepository {
    void updateMetadata(String token) throws APIException;
}