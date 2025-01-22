package org.gycoding.accounts.infrastructure.external.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.auth.UserMO;
import org.gycoding.accounts.domain.model.user.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.gyclient.FriendMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.gyclient.GYClientMetadataMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.infrastructure.external.unirest.UnirestFacade;
import org.gycoding.accounts.shared.AccountRoles;
import org.gycoding.accounts.shared.AuthConnections;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthFacadeImpl implements AuthFacade {
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

    private String getManagementToken() throws Auth0Exception {
        final var url   = this.managementTokenURL;
        final var body  = String.format(
                "{ \"client_id\": \"%s\", \"client_secret\": \"%s\", \"audience\": \"%s\", \"grant_type\": \"%s\" }",
                this.managementClientId,
                this.managementClientSecret,
                this.managementURL,
                "client_credentials"
        );

        final var response          = UnirestFacade.post(url, body);
        JSONObject jsonResponse     = new JSONObject(response.getBody());

        return jsonResponse.getString("access_token");
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
    public ProfileMO getProfile(String userId) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        final var user = managementAPI.users().get(userId, null).execute();

        return ProfileMO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .picture(user.getPicture())
                .roles(this.getRoles(userId))
                .build();
    }

    @Override
    public ProfileMO updateProfile(String userId, ProfileMO profile) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        var user = managementAPI.users().get(userId, null).execute();

        var updatedUser = new User();
        updatedUser.setName(profile.username());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPicture(profile.picture());
        updatedUser.setPhoneNumber(user.getPhoneNumber());

        managementAPI.users().update(userId, updatedUser).execute();

        return this.getProfile(userId);
    }

    @Override
    public String getUsername(String userId) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        final var user = managementAPI.users().get(userId, null).execute();

        return user.getName();
    }

    @Override
    public String updateUsername(String userId, String newUsername) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());

        User updateUser = new User();
        updateUser.setName(newUsername);

        managementAPI.users().update(userId, updateUser).execute();

        return newUsername;
    }

    @Override
    public String getEmail(String userId) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        final var user = managementAPI.users().get(userId, null).execute();

        return user.getEmail();
    }

    @Override
    public String updateEmail(String userId, String newEmail) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());

        User updateUser = new User();
        updateUser.setEmail(newEmail);

        managementAPI.users().update(userId, updateUser).execute();

        return newEmail;
    }

    @Override
    public String getPicture(String userId) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        final var user = managementAPI.users().get(userId, null).execute();

        return user.getPicture();
    }

    @Override
    public String updatePicture(String userId, String newPicture) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());

        User updateUser = new User();
        updateUser.setPicture(newPicture);

        managementAPI.users().update(userId, updateUser).execute();

        return newPicture;
    }

    @Override
    public String getPhoneNumber(String userId) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        final var user = managementAPI.users().get(userId, null).execute();

        return user.getPhoneNumber();
    }

    @Override
    public String updatePhoneNumber(String userId, String newPhoneNumber) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());

        User updateUser = new User();
        updateUser.setPhoneNumber(newPhoneNumber);

        managementAPI.users().update(userId, updateUser).execute();

        return newPhoneNumber;
    }

    @Override
    public void updatePassword(String userId, String newPassword) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());

        User updateUser = new User();
        updateUser.setPassword(newPassword);

        managementAPI.users().update(userId, updateUser).execute();
    }

    @Override
    public Map<String, Object> getMetadata(String userId) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        User user               = managementAPI.users().get(userId, null).execute();

        return user.getUserMetadata();
    }

    @Override
    public void setMetadata(String userId, Map<String, Object> metadata, Boolean isReset) throws Auth0Exception {
        if(Boolean.TRUE.equals(isReset) || getMetadata(userId) == null) {
            final var managementAPI      = new ManagementAPI(this.mainDomain, this.getManagementToken());
            final var user               = new User();

            user.setUserMetadata(metadata);

            Request<User> request        = managementAPI.users().update(userId, user);

            request.execute();
        }
    }

    @Override
    public List<AccountRoles> getRoles(String userId) throws Auth0Exception {
        final var metadata = this.getMetadata(userId);
        final var roles = ((List<String>) metadata.get("roles")).stream().map(AccountRoles::fromString).toList();

        return roles;
    }

    @Override
    public GYClientMetadataMO getClientMetadata(String userId) throws Auth0Exception {
        final var metadata = this.getMetadata(userId);

        return GYClientMetadataMO.builder()
                .title((String) ((HashMap<String, Object>) metadata.get("gyClient")).get("title"))
                .friends((List<FriendMetadataMO>) ((HashMap<String, Object>) metadata.get("gyClient")).get("friends"))
                .build();
    }
}
