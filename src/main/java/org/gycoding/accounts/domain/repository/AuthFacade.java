package org.gycoding.accounts.domain.repository;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.User;
import org.gycoding.accounts.domain.model.auth.UserMO;
import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthFacade {
    TokenHolder login(UserMO user) throws Auth0Exception;
    CreatedUser signUp(UserMO user) throws Auth0Exception;
    String googleAuth();
    TokenHolder handleGoogleResponse(String code) throws Auth0Exception;

    User getUser(String userId) throws Auth0Exception, APIException;

    void updatePassword(String userId, String newPassword) throws Auth0Exception, APIException;
}
