package org.gycoding.accounts.domain.repository;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.model.auth.UserMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.springframework.stereotype.Repository;

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

    String getPhoneNumber(String userId) throws Auth0Exception;
    String updatePhoneNumber(String userId, String newPhoneNumber) throws Auth0Exception;

    String getPicture(String userId) throws Auth0Exception;
    String updatePicture(String userId, String newPicture) throws Auth0Exception;

    void updatePassword(String userId, String newPassword) throws Auth0Exception;

    MetadataMO getMetadata(String userId) throws Auth0Exception;
    void setMetadata(String userId, MetadataMO metadata) throws Auth0Exception;

    void refreshApiKey(String userId) throws Auth0Exception;
}
