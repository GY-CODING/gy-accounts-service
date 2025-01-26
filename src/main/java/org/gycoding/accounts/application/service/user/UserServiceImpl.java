package org.gycoding.accounts.application.service.user;

import com.auth0.exception.Auth0Exception;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.MetadataIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.mapper.UserServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.domain.repository.PictureRepository;
import org.gycoding.accounts.shared.utils.FileUtils;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthFacade authFacade = null;

    @Qualifier("userServiceMapperImpl")
    @Autowired
    private UserServiceMapper mapper = null;

    @Autowired
    private PictureRepository pictureRepository = null;

    @Value("${gy.accounts.picture.url}")
    private String GY_ACCOUNTS_PICTURE_URL;

    @Override
    public ProfileODTO getProfile(String userId) throws APIException {
        try {
            return mapper.toODTO(authFacade.getProfile(userId));
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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

            return mapper.toODTO(pictureRepository.getPicture(userId + "-pfp"));
        } catch(Exception e) {
            log.error(e.getMessage());
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

            authFacade.updatePicture(userId, GY_ACCOUNTS_PICTURE_URL + formattedUserId);

            return mapper.toODTO(savedPicture);
        } catch(Exception e) {
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }
}
