package org.gycoding.accounts.infrastructure.external.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import kong.unirest.json.JSONObject;
import org.gycoding.accounts.domain.model.auth.UserMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.infrastructure.external.auth.mapper.AuthFacadeMapper;
import org.gycoding.accounts.infrastructure.external.unirest.UnirestFacade;
import org.gycoding.accounts.shared.AuthConnections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthFacadeImpl implements AuthFacade {
    @Qualifier("authFacadeMapperImpl")
    @Autowired
    private AuthFacadeMapper mapper = null;

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
        return this.getMetadata(userId).getProfile();
    }

    @Override
    public ProfileMO updateProfile(String userId, ProfileMO profile) throws Auth0Exception {
        final var metadata = this.getMetadata(userId);

        metadata.setProfile(profile);

        this.setMetadata(userId, metadata);

        return metadata.getProfile();
    }

    @Override
    public String getUsername(String userId) throws Auth0Exception {
        return this.getMetadata(userId).getProfile().username();
    }

    @Override
    public String updateUsername(String userId, String newUsername) throws Auth0Exception {
        final var metadata = this.getMetadata(userId);
        final var profile = ProfileMO.builder()
                .username(newUsername)
                .roles(metadata.getProfile().roles())
                .picture(metadata.getProfile().picture())
                .phoneNumber(metadata.getProfile().phoneNumber())
                .build();

        metadata.setProfile(profile);

        this.setMetadata(userId, metadata);

        return metadata.getProfile().username();
    }

    @Override
    public String getPicture(String userId) throws Auth0Exception {
        return this.getMetadata(userId).getProfile().picture();
    }

    @Override
    public String updatePicture(String userId, String newPicture) throws Auth0Exception {
        final var metadata = this.getMetadata(userId);
        final var profile = ProfileMO.builder()
                .username(metadata.getProfile().username())
                .roles(metadata.getProfile().roles())
                .picture(newPicture)
                .phoneNumber(metadata.getProfile().phoneNumber())
                .build();

        metadata.setProfile(profile);

        this.setMetadata(userId, metadata);

        return metadata.getProfile().username();
    }

    @Override
    public String getPhoneNumber(String userId) throws Auth0Exception {
        return this.getMetadata(userId).getProfile().phoneNumber();
    }

    @Override
    public String updatePhoneNumber(String userId, String newPhoneNumber) throws Auth0Exception {
        final var metadata = this.getMetadata(userId);
        final var profile = ProfileMO.builder()
                .username(metadata.getProfile().username())
                .roles(metadata.getProfile().roles())
                .picture(metadata.getProfile().picture())
                .phoneNumber(newPhoneNumber)
                .build();

        metadata.setProfile(profile);

        this.setMetadata(userId, metadata);

        return metadata.getProfile().phoneNumber();
    }

    @Override
    public void updatePassword(String userId, String newPassword) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());

        User updateUser = new User();
        updateUser.setPassword(newPassword);

        managementAPI.users().update(userId, updateUser).execute();
    }

    @Override
    public MetadataMO getMetadata(String userId) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        User user               = managementAPI.users().get(userId, null).execute();

        return mapper.toMO(user.getUserMetadata());
    }

    @Override
    public void setMetadata(String userId, MetadataMO metadata) throws Auth0Exception {
        final var managementAPI      = new ManagementAPI(this.mainDomain, this.getManagementToken());
        final var user               = new User();

        user.setUserMetadata(mapper.toMap(metadata));

        Request<User> request        = managementAPI.users().update(userId, user);

        request.execute();
    }
}
