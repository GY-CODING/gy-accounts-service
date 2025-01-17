package org.gycoding.accounts.application.service.user;

import org.gycoding.accounts.application.dto.in.user.metadata.MetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.ProfileODTO;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.gycoding.exceptions.model.APIException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
    ProfileODTO getProfile(String userId) throws APIException;
    ProfileODTO updateProfile(String userId, ProfileIDTO profile) throws APIException;

    String getUsername(String userId) throws APIException;
    String updateUsername(String userId, String username) throws APIException;

    String getEmail(String userId) throws APIException;
    String updateEmail(String userId, String email) throws APIException;

    PictureODTO getPicture(String userId) throws APIException;
    PictureODTO updatePicture(String userId, MultipartFile picture) throws APIException;

    String getPhoneNumber(String userId) throws APIException;
    String updatePhoneNumber(String userId, String phoneNumber) throws APIException;

    void updatePassword(String userId, String password) throws APIException;

    Object getMetadata(String userId) throws APIException;
    void updateMetadata(String userId, Optional<MetadataIDTO> metadata) throws APIException;
}