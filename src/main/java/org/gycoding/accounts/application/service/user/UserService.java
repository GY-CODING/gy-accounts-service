package org.gycoding.accounts.application.service.user;

import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.quasar.exceptions.model.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<ProfileODTO> listUsers(String query) throws ServiceException;
    List<ProfileODTO> listUsers(String userId, String query) throws ServiceException;

    ProfileODTO getProfile(String userId) throws ServiceException;
    ProfileODTO updateProfile(String userId, ProfileIDTO profile) throws ServiceException;

    String getUsername(String userId) throws ServiceException;
    String updateUsername(String userId, String username) throws ServiceException;

    String getEmail(String userId) throws ServiceException;

    PictureODTO getPicture(String userId) throws ServiceException;
    PictureODTO updatePicture(String userId, MultipartFile picture) throws ServiceException;

    String getPhoneNumber(String userId) throws ServiceException;
    String updatePhoneNumber(String userId, String phoneNumber) throws ServiceException;

    void updatePassword(String userId, String password) throws ServiceException;

    MetadataODTO getMetadata(String userId) throws ServiceException;
    MetadataODTO syncMetadata(String userId) throws ServiceException;

    String refreshApiKey(String userId) throws ServiceException;

    UUID transform(String userId) throws ServiceException;
    String transform(UUID profileId) throws ServiceException;
}