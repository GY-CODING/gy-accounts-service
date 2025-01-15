package org.gycoding.accounts.application.service.auth;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.domain.entities.metadata.UserMetadata;
import org.gycoding.accounts.domain.entities.model.auth.Profile;
import org.gycoding.accounts.infrastructure.dto.in.ProfileRSDTO;
import org.gycoding.accounts.infrastructure.dto.out.ProfileRQDTO;
import org.gycoding.exceptions.model.APIException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AuthRepository {
    TokenHolder login(String email, String password) throws APIException;
    CreatedUser signUp(String email, String username, String password) throws APIException;

    ProfileRSDTO updateProfile(String userId, ProfileRQDTO profile) throws APIException;
    void updateUsername(String userId, String username) throws APIException;
    void updateEmail(String userId, String email) throws APIException;
    void updatePassword(String userId, String password) throws APIException;
    EntityPicture updatePicture(String userId, MultipartFile picture) throws APIException;

    String updatePhoneNumber(String userId, String phoneNumber) throws APIException;

    EntityPicture getPicture(String userId) throws APIException;

    Profile getUserProfile(String userId) throws APIException;

    String googleAuth() throws APIException;
    TokenHolder handleGoogleResponse(String code) throws APIException;

    void updateMetadata(String userId, Optional<UserMetadata> metadata) throws APIException;
}