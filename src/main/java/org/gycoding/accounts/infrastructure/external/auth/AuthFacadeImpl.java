package org.gycoding.accounts.infrastructure.external.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.User;
import kong.unirest.json.JSONObject;
import org.apiguardian.api.API;
import org.gycoding.accounts.domain.model.auth.UserMO;
import org.gycoding.accounts.domain.repository.Auth0FeignFacade;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.shared.AuthConnections;
import org.gycoding.exceptions.model.APIException;
import org.gycoding.logs.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthFacadeImpl implements AuthFacade {
    @Autowired
    private Auth0FeignFacade auth0FeignFacade;

    private AuthAPI authAPI;

    private String mainDomain;
    private String mainClientId;
    private String mainClientSecret;

    private String managementURL;
    private String managementClientId;
    private String managementClientSecret;
    private String managementTokenURL;
    private String googleCallbackURL;

    private String userinfoURL;

    public AuthFacadeImpl(
            @Value("${auth0.main.domain}") String mainDomain,
            @Value("${auth0.main.clientId}") String mainClientId,
            @Value("${auth0.main.clientSecret}") String mainClientSecret,
            @Value("${auth0.management.url}") String managementURL,
            @Value("${auth0.management.clientId}") String managementClientId,
            @Value("${auth0.management.clientSecret}") String managementClientSecret,
            @Value("${auth0.management.token.url}") String managementTokenURL,
            @Value("${auth0.google.callback}") String googleCallbackURL,
            @Value("${auth0.userinfo.url}") String userinfoURL
    ) {
        this.mainDomain             = mainDomain;
        this.mainClientId           = mainClientId;
        this.mainClientSecret       = mainClientSecret;
        this.managementURL          = managementURL;
        this.managementClientId     = managementClientId;
        this.managementClientSecret = managementClientSecret;
        this.managementTokenURL     = managementTokenURL;
        this.googleCallbackURL      = googleCallbackURL;
        this.userinfoURL            = userinfoURL;

        this.authAPI                = new AuthAPI(this.mainDomain, this.mainClientId, this.mainClientSecret);
    }

    private String getManagementToken() throws APIException {
        final var accessToken = auth0FeignFacade.getManagementToken(
                this.managementClientId,
                this.managementClientSecret,
                this.managementURL,
                "client_credentials"
        );

        Logger.info("Management access token generated.", new JSONObject().put("token", accessToken));

        return accessToken;
    }

    @Override
    public TokenHolder login(UserMO user) throws Auth0Exception {
        return authAPI.login(user.email(), user.password()).execute();
    }

    @Override
    public CreatedUser signUp(UserMO user) throws Auth0Exception {
        return authAPI.signUp(user.email(), user.username(), user.password(), AuthConnections.BASIC.name).execute();
    }

    @Override
    public String googleAuth() {
        return authAPI.authorizeUrl(googleCallbackURL)
                .withConnection(AuthConnections.GOOGLE.name)
                .build();
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws Auth0Exception {
        return authAPI.exchangeCode(code, googleCallbackURL).execute();
    }

    @Override
    public User getUser(String userId) throws Auth0Exception, APIException {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        return managementAPI.users().get(userId, null).execute();
    }

    @Override
    public void updatePassword(String userId, String newPassword) throws Auth0Exception, APIException {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());

        User updateUser = new User();
        updateUser.setPassword(newPassword);

        managementAPI.users().update(userId, updateUser).execute();
    }
}
