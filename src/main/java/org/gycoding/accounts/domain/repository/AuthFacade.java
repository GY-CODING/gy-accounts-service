package org.gycoding.accounts.domain.repository;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.model.auth.UserMO;
import org.gycoding.accounts.domain.model.user.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.gyclient.GYClientMetadataMO;
import org.gycoding.accounts.shared.AccountRoles;
import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthFacade {
    TokenHolder login(UserMO user) throws Auth0Exception;
    CreatedUser signUp(UserMO user) throws Auth0Exception;
    String googleAuth();
    TokenHolder handleGoogleResponse(String code) throws Auth0Exception;

    ProfileMO getProfile(String userId) throws Auth0Exception;
    ProfileMO updateProfile(String userId, ProfileMO profile) throws Auth0Exception;

    String getUsername(String userId) throws Auth0Exception;
    String updateUsername(String userId, String newUsername) throws Auth0Exception;

    String getEmail(String userId) throws Auth0Exception;
    String updateEmail(String userId, String newEmail) throws Auth0Exception;

    String getPhoneNumber(String userId) throws Auth0Exception;
    String updatePhoneNumber(String userId, String newPhoneNumber) throws Auth0Exception;

    String getPicture(String userId) throws Auth0Exception;
    String updatePicture(String userId, String newPicture) throws Auth0Exception;

    void updatePassword(String userId, String newPassword) throws Auth0Exception;

    Map<String, Object> getMetadata(String userId) throws Auth0Exception;
    void setMetadata(String userId, Map<String, Object> metadata, Boolean isReset) throws Auth0Exception;
    List<AccountRoles> getRoles(String userId) throws Auth0Exception;

    GYClientMetadataMO getClientMetadata(String userId) throws Auth0Exception;
}
