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
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.domain.repository.MetadataRepository;
import org.gycoding.accounts.domain.repository.PictureRepository;
import org.gycoding.accounts.shared.utils.Base64Utils;
import org.gycoding.accounts.shared.utils.FileUtils;
import org.gycoding.exceptions.model.APIException;
import org.gycoding.logs.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private UserServiceMapper mapper;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    @Value("${gy.accounts.picture.url}")
    private String GY_ACCOUNTS_PICTURE_URL;

    @Override
    public List<ProfileODTO> listUsers(String query) throws APIException {
        try {
            return metadataRepository.list(query).stream()
                    .map(MetadataMO::profile)
                    .map(mapper::toODTO)
                    .toList();
        } catch (Exception e) {
            throw new APIException(
                    AccountsAPIError.SERVER_ERROR.getCode(),
                    AccountsAPIError.SERVER_ERROR.getMessage(),
                    AccountsAPIError.SERVER_ERROR.getStatus()
            );
        }
    }

    @Override
    public List<ProfileODTO> listUsers(String userId, String query) throws APIException {
        final var requestedProfiles = metadataRepository.list(query).stream()
                .map(MetadataMO::profile)
                .toList();

        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        try {
            return requestedProfiles.stream()
                    .map(profile -> mapper.toODTO(profile, userMetadata.books().friends().contains(profile.id())))
                    .toList();
        } catch(NullPointerException e) {
            return requestedProfiles.stream()
                    .map(mapper::toODTO)
                    .toList();
        }
    }

    @Override
    public ProfileODTO getProfile(String userId) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return mapper.toODTO(userMetadata.profile());
    }

    @Override
    public ProfileODTO updateProfile(String userId, ProfileIDTO profile) throws APIException {
        MetadataMO updatedMetadata = null;

        if(!(Objects.equals(profile.picture(), ""))) {
            final var picture = updatePicture(userId, FileUtils.read(profile.picture()));

            updatedMetadata = metadataRepository.update(
                    MetadataMO.builder()
                            .userId(userId)
                            .profile(mapper.toMO(profile, GY_ACCOUNTS_PICTURE_URL + picture.name().replace("-pfp", "")))
                            .build()
            );
        } else {
            updatedMetadata = metadataRepository.update(
                    MetadataMO.builder()
                            .userId(userId)
                            .profile(mapper.toMO(profile))
                            .build()
            );
        }

        return mapper.toODTO(updatedMetadata.profile());
    }

    @Override
    public String getUsername(String userId) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return userMetadata.profile().username();
    }

    @Override
    public String updateUsername(String userId, String username) throws APIException {
        final var userMetadata = metadataRepository.update(
                MetadataMO.builder()
                        .userId(userId)
                        .profile(
                                ProfileMO.builder()
                                        .username(username)
                                        .build()
                        )
                        .build()
        );

        return userMetadata.profile().username();
    }

    @Override
    public String getEmail(String userId) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return userMetadata.profile().email();
    }

    @Override
    public PictureODTO getPicture(String userId) {
        userId = userId.replace("auth0|", "");
        userId = userId.replace("google-oauth2|", "");

        return mapper.toODTO(pictureRepository.get(userId + "-pfp"));
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

            Logger.info("User profile picture has been successfully saved to the database.", new JSONObject().put("userId", userId));

            final var updatedMetadata = metadataRepository.update(
                    MetadataMO.builder()
                            .userId(userId)
                            .profile(
                                    ProfileMO.builder()
                                            .picture(GY_ACCOUNTS_PICTURE_URL + savedPicture.name().replace("-pfp", ""))
                                            .build()
                            )
                            .build()
            );

            Logger.info("User profile picture has been successfully linked with the metadata.", new JSONObject().put("userId", userId));

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
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return userMetadata.profile().phoneNumber();
    }

    @Override
    public String updatePhoneNumber(String userId, String phoneNumber) throws APIException {
        final var userMetadata = metadataRepository.update(
                MetadataMO.builder()
                        .userId(userId)
                        .profile(
                                ProfileMO.builder()
                                        .phoneNumber(phoneNumber)
                                        .build()
                        )
                        .build()
        );

        return userMetadata.profile().phoneNumber();
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
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return mapper.toODTO(userMetadata);
    }

    @Override
    public MetadataODTO syncMetadata(String userId) throws APIException {
        try {
            final var userMetadata = metadataRepository.get(userId);
            final var user = authFacade.getUser(userId);

            if(userMetadata.isPresent()) {
                return mapper.toODTO(
                        metadataRepository.refresh(
                                mapper.toDefaultMO(
                                        userId,
                                        user,
                                        userMetadata.get().profile().picture().isBlank() ? GY_ACCOUNTS_PICTURE_URL + updatePicture(userId, FileUtils.read(user.getPicture())).name().replace("-pfp", "") : userMetadata.get().profile().picture()
                                )
                        )
                );
            }

            return mapper.toODTO(
                    metadataRepository.save(
                            mapper.toDefaultMO(
                                    userId,
                                    user,
                                    GY_ACCOUNTS_PICTURE_URL + updatePicture(userId, FileUtils.read(user.getPicture())).name().replace("-pfp", "")
                            )
                    )
            );
        } catch(Exception e) {
            Logger.error("An error has occurred while setting user metadata.", new JSONObject().put("error", e.getMessage()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public String refreshApiKey(String userId) throws APIException {
        final var userMetadata = metadataRepository.update(
                MetadataMO.builder()
                        .userId(userId)
                        .profile(
                                ProfileMO.builder()
                                        .apiKey(Base64Utils.generateApiKey())
                                        .build()
                        )
                        .build()
        );

        return userMetadata.profile().apiKey();
    }

    @Override
    public String decodeApiKey(String apiKey) throws APIException {
        final var userMetadata = metadataRepository.getByApiKey(apiKey)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return userMetadata.userId();
    }

    @Override
    public UUID transform(String userId) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return userMetadata.profile().id();
    }

    @Override
    public String transform(UUID profileId) throws APIException {
        final var userMetadata = metadataRepository.get(profileId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return userMetadata.userId();
    }
}
