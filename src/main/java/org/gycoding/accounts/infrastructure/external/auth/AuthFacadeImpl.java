package org.gycoding.accounts.infrastructure.external.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import org.gycoding.accounts.domain.enums.AuthConnections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthFacadeImpl implements AuthFacade {
    private AuthAPI authAPI;

    private String domain;
    private String googleCallbackURL;
    private String managementURL;

    public AuthFacadeImpl(
            @Value("${auth0.domain}") String domain,
            @Value("${auth0.clientId}") String clientId,
            @Value("${auth0.clientSecret}") String clientSecret,
            @Value("${auth0.managementUrl}") String managementURL,
            @Value("${auth0.google.callback}") String googleCallbackURL
    ) {
        this.googleCallbackURL  = googleCallbackURL;
        this.managementURL      = managementURL;
        this.domain             = domain;
        this.authAPI            = new AuthAPI(domain, clientId, clientSecret);
    }

    private ManagementAPI getManagementAPI() throws Auth0Exception {
        TokenHolder token = authAPI.requestToken(managementURL).execute();
        return new ManagementAPI(domain, token.getAccessToken());
    }

    @Override
    public String googleAuth() {
        String authorizeUrl = authAPI.authorizeUrl(googleCallbackURL)
                .withConnection(AuthConnections.GOOGLE.name)
                .build();

        return authorizeUrl;
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws Auth0Exception {
        final var authRequest = authAPI.exchangeCode(code, googleCallbackURL);
        return authRequest.execute();
    }

    @Override
    public TokenHolder login(String email, String password) throws Auth0Exception {
        final var authRequest = authAPI.login(email, password);
        return authRequest.execute();
    }

    @Override
    public CreatedUser signUp(String email, String username, String password) throws Auth0Exception {
        final var authRequest = authAPI.signUp(email, username, password, AuthConnections.BASIC.name);
        return authRequest.execute();
    }

    @Override
    public Map<String, Object> getMetadata(String userId) throws Auth0Exception {
        final var managementAPI = getManagementAPI();
        User user               = managementAPI.users().get(userId, null).execute();

        return user.getUserMetadata();
    }


    @Override
    public void updateMetadata(String userId, Map<String, Object> metadata) throws Auth0Exception {
        final var managementAPI      = getManagementAPI();
        final var user               = managementAPI.users().get(userId, null).execute();

        user.setUserMetadata(metadata);

        Request<User> request   = managementAPI.users().update(userId, user);

        request.execute();
    }
}
