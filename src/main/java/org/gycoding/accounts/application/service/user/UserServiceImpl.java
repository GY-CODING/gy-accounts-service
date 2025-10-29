package org.gycoding.accounts.application.service.user;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.mapper.UserServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsError;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.domain.repository.BooksFeignFacade;
import org.gycoding.accounts.domain.repository.MetadataRepository;
import org.gycoding.accounts.domain.repository.PictureRepository;
import org.gycoding.accounts.shared.utils.Base64Utils;
import org.gycoding.accounts.shared.utils.FileUtils;
import org.gycoding.quasar.exceptions.model.DatabaseException;
import org.gycoding.quasar.exceptions.model.FacadeException;
import org.gycoding.quasar.logs.service.Logger;
import org.gycoding.quasar.exceptions.model.ServiceException;
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
    private BooksFeignFacade booksFeignFacade;

    @Autowired
    private UserServiceMapper mapper;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    @Value("${gy.accounts.picture.url}")
    private String GY_ACCOUNTS_PICTURE_URL;

    @Override
    public List<ProfileODTO> listUsers(String query) throws ServiceException {
        try {
            return metadataRepository.list(query).stream()
                    .map(MetadataMO::profile)
                    .map(mapper::toODTO)
                    .toList();
        } catch (Exception e) {
            throw new ServiceException(AccountsError.SERVER_ERROR);
        }
    }

    @Override
    public List<ProfileODTO> listUsers(String userId, String query) throws ServiceException {
        final var requestedProfiles = metadataRepository.list(query).stream()
                .map(MetadataMO::profile)
                .toList();

        Logger.info("All the profiles have been successfully gathered.", userId);

        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND));

        Logger.info("User metadata has been successfully retrieved.", userId);

        try {
            return requestedProfiles.stream()
                    .map(profile -> mapper.toODTO(profile))
                    .toList();
        } catch(NullPointerException e) {
            return requestedProfiles.stream()
                    .map(mapper::toODTO)
                    .toList();
        }
    }

    @Override
    public ProfileODTO getProfile(String userId) throws ServiceException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND));

        Logger.info("User metadata has been successfully retrieved.", userId);

        return mapper.toODTO(userMetadata.profile());
    }

    @Override
    public ProfileODTO updateProfile(String userId, ProfileIDTO profile) throws ServiceException {
        MetadataMO updatedMetadata = null;
        try {
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
        } catch(DatabaseException | ServiceException e) {
            throw new ServiceException(AccountsError.METADATA_NOT_MODIFIED);
        }

        Logger.info("User metadata has been successfully updated.", userId);

        return mapper.toODTO(updatedMetadata.profile());
    }

    @Override
    public String getUsername(String userId) throws ServiceException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND));

        Logger.info("User metadata has been successfully retrieved.", userId);

        return userMetadata.profile().username();
    }

    @Override
    public String updateUsername(String userId, String username) throws ServiceException {
        final MetadataMO userMetadata;

        try {
            userMetadata = metadataRepository.update(
                    MetadataMO.builder()
                            .userId(userId)
                            .profile(
                                    ProfileMO.builder()
                                            .username(username)
                                            .build()
                            )
                            .build()
            );
        } catch(DatabaseException e) {
            throw new ServiceException(AccountsError.METADATA_NOT_MODIFIED);
        }

        Logger.info("User metadata has been successfully updated.", userId);

        return userMetadata.profile().username();
    }

    @Override
    public String getEmail(String userId) throws ServiceException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND));

        Logger.info("User metadata has been successfully retrieved.", userId);

        return userMetadata.profile().email();
    }

    @Override
    public PictureODTO getPicture(String userId) {
        userId = userId.replace("auth0|", "");
        userId = userId.replace("google-oauth2|", "");

        return mapper.toODTO(pictureRepository.get(userId + "-pfp"));
    }

    @Override
    public PictureODTO updatePicture(String userId, MultipartFile picture) throws ServiceException {
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

            Logger.info("User profile picture has been successfully saved to the database.", userId);

            metadataRepository.update(
                    MetadataMO.builder()
                            .userId(userId)
                            .profile(
                                    ProfileMO.builder()
                                            .picture(GY_ACCOUNTS_PICTURE_URL + savedPicture.name().replace("-pfp", ""))
                                            .build()
                            )
                            .build()
            );

            Logger.info("User profile picture has been successfully linked with the metadata.", userId);

            return mapper.toODTO(savedPicture);
        } catch(Exception e) {
            Logger.error("An error has occurred while updating user profile picture.", userId);

            throw new ServiceException(AccountsError.RESOURCE_NOT_MODIFIED);
        }
    }

    @Override
    public String getPhoneNumber(String userId) throws ServiceException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND));

        Logger.info("User metadata has been successfully retrieved.", userId);

        return userMetadata.profile().phoneNumber();
    }

    @Override
    public String updatePhoneNumber(String userId, String phoneNumber) throws ServiceException {
        final MetadataMO userMetadata;

        try {
            userMetadata = metadataRepository.update(
                    MetadataMO.builder()
                            .userId(userId)
                            .profile(
                                    ProfileMO.builder()
                                            .phoneNumber(phoneNumber)
                                            .build()
                            )
                            .build()
            );
        } catch(DatabaseException e) {
            throw new ServiceException(AccountsError.METADATA_NOT_MODIFIED);
        }

        Logger.info("User metadata has been successfully updated.", userId);

        return userMetadata.profile().phoneNumber();
    }

    @Override
    public void updatePassword(String userId, String password) throws ServiceException {
        try {
            authFacade.updatePassword(userId, password);
        } catch(Auth0Exception | FacadeException e) {
            throw new ServiceException(AccountsError.PASSWORD_NOT_MODIFIED);
        }

        Logger.info("User password has been successfully updated.", userId);
    }

    @Override
    public MetadataODTO getMetadata(String userId) throws ServiceException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND));

        Logger.info("User metadata has been successfully retrieved.", userId);

        return mapper.toODTO(userMetadata);
    }

    @Override
    public MetadataODTO syncMetadata(String userId) throws ServiceException {
        final User user;

        try {
            user = authFacade.getUser(userId);
        } catch(Auth0Exception | FacadeException e) {
            throw new ServiceException(AccountsError.USER_NOT_FOUND);
        }

        final var userMetadata = metadataRepository.get(userId);
        final var defaultMetadata = mapper.toDefaultMO(userId, user);

        Logger.info("User metadata has been successfully retrieved.", userId);

        MetadataMO savedMetadata;

        if(userMetadata.isPresent()) {
            try {
                savedMetadata = metadataRepository.refresh(defaultMetadata);
            } catch(DatabaseException e) {
                throw new ServiceException(AccountsError.METADATA_NOT_MODIFIED);
            }

            Logger.info("User metadata has been successfully refreshed.", userId);
        } else {
            savedMetadata = metadataRepository.save(defaultMetadata);

            Logger.info("User metadata has been successfully set.", userId);
        }

        try {
            booksFeignFacade.setMetadata(savedMetadata.profile().id());
        } catch(FacadeException e) {
            throw new ServiceException(AccountsError.METADATA_NOT_SAVED);
        }

        Logger.debug("User products metadata has been successfully set or refreshed.", userId);

        updatePicture(userId, FileUtils.read(user.getPicture()));

        Logger.info("User picture has been successfully set.", userId);

        return mapper.toODTO(
                metadataRepository.get(userId)
                        .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND))
        );
    }

    @Override
    public String refreshApiKey(String userId) throws ServiceException {
        final MetadataMO userMetadata;

        try {
            userMetadata = metadataRepository.update(
                    MetadataMO.builder()
                            .userId(userId)
                            .profile(
                                    ProfileMO.builder()
                                            .apiKey(Base64Utils.generateApiKey())
                                            .build()
                            )
                            .build()
            );
        } catch(DatabaseException e) {
            throw new ServiceException(AccountsError.METADATA_NOT_MODIFIED);
        }

        Logger.info("User metadata has been successfully updated.", userId);

        return userMetadata.profile().apiKey();
    }

    @Override
    public UUID transform(String userId) throws ServiceException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND));

        Logger.info("User metadata has been successfully retrieved.", userId);

        return userMetadata.profile().id();
    }

    @Override
    public String transform(UUID profileId) throws ServiceException {
        final var userMetadata = metadataRepository.get(profileId)
                .orElseThrow(() -> new ServiceException(AccountsError.RESOURCE_NOT_FOUND));

        Logger.info("User metadata has been successfully retrieved.", profileId.toString());

        return userMetadata.userId();
    }
}
