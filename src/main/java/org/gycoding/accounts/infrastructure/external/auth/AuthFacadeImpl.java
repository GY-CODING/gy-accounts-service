package org.gycoding.accounts.infrastructure.external.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import kong.unirest.json.JSONObject;
import org.gycoding.accounts.domain.model.auth.UserMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.repository.ApiKeyRepository;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.infrastructure.external.auth.mapper.AuthFacadeMapper;
import org.gycoding.accounts.infrastructure.external.unirest.UnirestFacade;
import org.gycoding.accounts.shared.AuthConnections;
import org.gycoding.accounts.shared.utils.Base64Utils;
import org.gycoding.logs.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuthFacadeImpl implements AuthFacade {
    @Autowired
    private AuthFacadeMapper mapper;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

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
        final var accessToken       = jsonResponse.getString("access_token");

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
    public ProfileMO getProfile(String userId) throws Auth0Exception {
        return this.getMetadata(userId).getProfile();
    }

    @Override
    public ProfileMO updateProfile(String userId, ProfileMO profile) throws Auth0Exception {
        final var metadata = this.getMetadata(userId);

        metadata.setProfile(
                ProfileMO.builder()
                        .username(profile.username())
                        .roles(metadata.getProfile().roles())
                        .apiKey(metadata.getProfile().apiKey())
                        .picture(metadata.getProfile().picture())
                        .phoneNumber(profile.phoneNumber())
                        .build()
        );

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
                .apiKey(metadata.getProfile().apiKey())
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
    public List<ProfileMO> listUsers(String query) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        return managementAPI
                .users()
                .list(new UserFilter())
                .execute()
                .getItems()
                .stream()
                .filter(user -> user.getUserMetadata().containsKey("books"))
                .filter(user -> mapper.toMO(user.getUserMetadata()).getProfile().username().equalsIgnoreCase(query))
                .map(user -> mapper.toMO(user.getUserMetadata()).getProfile())
                .toList();
    }

    @Override
    public List<ProfileMO> listUsers(String userId, String query) throws Auth0Exception {
        final var managementAPI = new ManagementAPI(this.mainDomain, this.getManagementToken());
        return managementAPI
                .users()
                .list(new UserFilter())
                .execute()
                .getItems()
                .stream()
                .filter(user -> !user.getId().equals(userId))
                .filter(user -> user.getUserMetadata().containsKey("books"))
                .filter(user -> mapper.toMO(user.getUserMetadata()).getProfile().username().equalsIgnoreCase(query))
                .map(user -> {
                    final var profile = mapper.toMO(user.getUserMetadata()).getProfile();

                    return ProfileMO.builder()
                            .id(profile.id())
                            .username(profile.username())
                            .roles(profile.roles())
                            .picture(profile.picture())
                            .phoneNumber(profile.phoneNumber())
                            .apiKey(profile.apiKey())
                            .isFriend(mapper.toMO(user.getUserMetadata()).getBooks().friends().contains(userId))
                            .build();
                })
                .toList();
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
        final var user               = managementAPI.users().get(userId, null).execute();
        var updatedUser              = new User();

        // TODO. These conditions are used for fields that are empty, either use a "class" entity object or do a better mapping.
        if(Objects.equals(metadata.getProfile().username(), "")) {
            metadata.setProfile(
                    ProfileMO.builder()
                            .id(metadata.getProfile().id())
                            .username(user.getName())
                            .roles(metadata.getProfile().roles())
                            .picture(metadata.getProfile().picture())
                            .phoneNumber(metadata.getProfile().phoneNumber())
                            .apiKey(metadata.getProfile().apiKey())
                            .build());
        }

        if(Objects.equals(metadata.getProfile().picture(), "")) {
            metadata.setProfile(
                    ProfileMO.builder()
                            .id(metadata.getProfile().id())
                            .username(metadata.getProfile().username())
                            .roles(metadata.getProfile().roles())
                            .picture(user.getPicture())
                            .phoneNumber(metadata.getProfile().phoneNumber())
                            .apiKey(metadata.getProfile().apiKey())
                            .build());
        }

        if(Objects.equals(metadata.getProfile().apiKey(), "")) {
            metadata.setProfile(
                    ProfileMO.builder()
                            .id(metadata.getProfile().id())
                            .username(metadata.getProfile().username())
                            .roles(metadata.getProfile().roles())
                            .picture(metadata.getProfile().picture())
                            .phoneNumber(metadata.getProfile().phoneNumber())
                            .apiKey(Base64Utils.generateApiKey())
                            .build());
        }

        updatedUser.setUserMetadata(mapper.toMap(metadata));
        apiKeyRepository.save(userId, metadata.getProfile().apiKey());

        Request<User> request = managementAPI.users().update(userId, updatedUser);

        request.execute();
    }

    @Override
    public String refreshApiKey(String userId) throws Auth0Exception {
        final var metadata = this.getMetadata(userId);

        final var apiKey = Base64Utils.generateApiKey();

        final var profile = ProfileMO.builder()
                .username(metadata.getProfile().username())
                .roles(metadata.getProfile().roles())
                .picture(metadata.getProfile().picture())
                .phoneNumber(metadata.getProfile().phoneNumber())
                .apiKey(apiKey)
                .build();

        metadata.setProfile(profile);

        this.setMetadata(userId, metadata);

        return apiKey;
    }

    @Override
    public String decodeApiKey(String apiKey) {
        return apiKeyRepository.getUserId(apiKey);
    }
}
