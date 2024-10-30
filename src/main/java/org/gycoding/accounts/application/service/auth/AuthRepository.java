package org.gycoding.accounts.application.service.auth;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.domain.entities.database.EntityUsername;
import org.gycoding.accounts.domain.entities.metadata.UserMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;
import org.gycoding.exceptions.model.APIException;
import org.springframework.web.multipart.MultipartFile;

public interface AuthRepository {
    TokenHolder login(String email, String password) throws APIException;
    CreatedUser signUp(String email, String username, String password) throws APIException;

    EntityUsername updateUsername(String userId, String username) throws APIException;
    void updateEmail(String userId, String email) throws APIException;
    void updatePassword(String userId, String password) throws APIException;
    EntityPicture updatePicture(String userId, MultipartFile picture) throws APIException;
    EntityPicture getPicture(String userId) throws APIException;

    String googleAuth() throws APIException;
    TokenHolder handleGoogleResponse(String code) throws APIException;
}