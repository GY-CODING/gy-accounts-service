package org.gycoding.accounts.application.service.user;

import com.auth0.exception.Auth0Exception;
import kong.unirest.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.mapper.UserServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.domain.repository.PictureRepository;
import org.gycoding.accounts.shared.utils.FileUtils;
import org.gycoding.exceptions.model.APIException;
import org.gycoding.logs.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private UserServiceMapper mapper;

    @Autowired
    private PictureRepository pictureRepository;

    @Value("${gy.accounts.picture.url}")
    private String GY_ACCOUNTS_PICTURE_URL;

    @Override
    public ProfileODTO getProfile(String userId) throws APIException {
        try {
            return mapper.toODTO(authFacade.getProfile(userId));
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while retrieving user profile.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public ProfileODTO updateProfile(String userId, ProfileIDTO profile) throws APIException {
        try {
            if(!(Objects.equals(profile.picture(), ""))) {
                this.updatePicture(userId, FileUtils.read(profile.picture()));
            }

            return mapper.toODTO(authFacade.updateProfile(userId, mapper.toMO(profile)));
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while updating user profile.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public String getUsername(String userId) throws APIException {
        try {
            return authFacade.getUsername(userId);
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while retrieving user username.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public String updateUsername(String userId, String username) throws APIException {
        try {
            return authFacade.updateUsername(userId, username);
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while updating user username.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public PictureODTO getPicture(String userId) throws APIException {
        try {
            userId = userId.replace("auth0|", "");
            userId = userId.replace("google-oauth2|", "");

            return mapper.toODTO(pictureRepository.get(userId + "-pfp"));
        } catch(Exception e) {
            Logger.error("An error has occurred while retrieving user profile picture.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public PictureODTO updatePicture(String userId, MultipartFile picture) throws APIException {
        try {
            var formattedUserId = userId;
            formattedUserId = formattedUserId.replace("auth0|", "");
            formattedUserId = formattedUserId.replace("google-oauth2|", "");

            final var savedPicture = pictureRepository.save(
                    PictureMO.builder()
                            .name(formattedUserId + "-pfp")
                            .contentType(picture.getContentType())
                            .picture(new Binary(BsonBinarySubType.BINARY, picture.getBytes()))
                            .build()
            );

            Logger.error("User profile picture has been successfully saved to the database.", new JSONObject().put("userId", userId));

            authFacade.updatePicture(userId, GY_ACCOUNTS_PICTURE_URL + formattedUserId);

            return mapper.toODTO(savedPicture);
        } catch(Exception e) {
            Logger.error("An error has occurred while updating user profile picture.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public String getPhoneNumber(String userId) throws APIException {
        try {
            return authFacade.getPhoneNumber(userId);
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while retrieving user phone number.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public String updatePhoneNumber(String userId, String phoneNumber) throws APIException {
        try {
            return authFacade.updatePhoneNumber(userId, phoneNumber);
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while updating user phone number.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public void updatePassword(String userId, String password) throws APIException {
        try {
            authFacade.updatePassword(userId, password);
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while updating user password.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public MetadataODTO getMetadata(String userId) throws APIException {
        try {
            return mapper.toODTO(authFacade.getMetadata(userId));
        } catch(Exception e) {
            Logger.error("An error has occurred while retrieving user metadata.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public void updateMetadata(String userId) throws APIException {
        try {
            authFacade.setMetadata(userId, authFacade.getMetadata(userId));
        } catch(Exception e) {
            Logger.error("An error has occurred while refreshing user metadata.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public String refreshApiKey(String userId) throws APIException {
        try {
            return authFacade.refreshApiKey(userId);
        } catch(Exception e) {
            Logger.error("An error has occurred while updating user API key.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public String decodeApiKey(String apiKey) throws APIException {
        try {
            return authFacade.decodeApiKey(apiKey);
        } catch(Exception e) {
            Logger.error("An error has occurred while decoding user API key.", new JSONObject().put("error", e.getMessage()).put("key", apiKey));

            throw new APIException(
                    AccountsAPIError.CONFLICT.getCode(),
                    AccountsAPIError.CONFLICT.getMessage(),
                    AccountsAPIError.CONFLICT.getStatus()
            );
        }
    }
}
