package org.gycoding.accounts.model.database;

import org.gycoding.accounts.model.entities.*;
import org.gycoding.accounts.model.util.ByteHexConverter;
import org.gycoding.accounts.model.util.Cipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    /* ===============# CRUD Methods #=============== */

    public List<User> listUsers() {
        return accountRepository.findAll();
    }

    public User getUser(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    public User saveUser(User usuario) {
        return accountRepository.save(usuario);
    }

    public void deleteUser(String email) {
        accountRepository.deleteByEmail(email);
    }


    /* ===============# Custom Methods #=============== */

    public ServerStatus checkLogin(String email, String password) {
        User user = getUser(email);

        if(user != null) {
            if(Cipher.verifyPassword(password, ByteHexConverter.hexToBytes(user.salt()), ByteHexConverter.hexToBytes(user.password()))) {
                return ServerStatus.USER_LOGGED_IN;
            } else {
                return ServerStatus.INVALID_LOGIN;
            }
        } else {
            return ServerStatus.USER_NOT_FOUND;
        }
    }

    public ServerStatus signUp(User user, String password) {
        if(this.checkLogin(user.email(), password).equals(ServerStatus.USER_LOGGED_IN)) {
            return ServerStatus.INVALID_SIGNUP;
        } else {
            final byte[] salt   = Cipher.generateSalt();

            final User newUser  = User.builder()
                    .username(user.username())
                    .email(user.email())
                    .salt(ByteHexConverter.bytesToHex(salt))
                    .password(ByteHexConverter.bytesToHex(Cipher.hashPassword(password, salt)))
                    .build();

            saveUser(newUser);

            return ServerStatus.USER_REGISTERED;
        }
    }

    public Session getSession(String email, String password) {
        if(this.checkLogin(email, password).equals(ServerStatus.USER_LOGGED_IN)) {
            return new Session(this.getUser(email));
        } else {
            return null;
        }
    }

    public ServerStatus updateUsername(String email, String password, String newUsername) {
        if(this.checkLogin(email, password).equals(ServerStatus.USER_LOGGED_IN)) {
            User user = getUser(email);

            try {
                final User modifiedUser = User.builder()
                        .username(newUsername)
                        .email(user.email())
                        .salt(user.salt())
                        .password(user.password())
                        .build();

                saveUser(modifiedUser);

                return ServerStatus.USERNAME_UPDATE;
            } catch(Exception e) {
                return ServerStatus.USERNAME_UPDATE_ERROR;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updateEmail(String email, String password, String newEmail) {
        if(this.checkLogin(email, password).equals(ServerStatus.USER_LOGGED_IN)) {
            User user = getUser(email);

            try {
                final User modifiedUser = User.builder()
                        .username(user.username())
                        .email(newEmail)
                        .salt(user.salt())
                        .password(user.password())
                        .build();

                saveUser(modifiedUser);

                return ServerStatus.EMAIL_UPDATE;
            } catch(Exception e) {
                return ServerStatus.EMAIL_UPDATE_ERROR;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updatePassword(String email, String password, String newPassword) {
        if(this.checkLogin(email, password).equals(ServerStatus.USER_LOGGED_IN)) {
            User user = getUser(email);

            try {
                final User modifiedUser = User.builder()
                        .username(user.username())
                        .email(user.email())
                        .salt(user.salt())
                        .password(ByteHexConverter.bytesToHex(Cipher.hashPassword(newPassword, ByteHexConverter.hexToBytes(user.salt()))))
                        .build();

                saveUser(modifiedUser);

                return ServerStatus.PASSWORD_UPDATE;
            } catch(Exception e) {
                return ServerStatus.PASSWORD_UPDATE_ERROR;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updatePasswordForgotten(String email, String newPassword) {
        try {
            User user = getUser(email);

            final User modifiedUser = User.builder()
                    .username(user.username())
                    .email(user.email())
                    .salt(user.salt())
                    .password(ByteHexConverter.bytesToHex(Cipher.hashPassword(newPassword, ByteHexConverter.hexToBytes(user.salt()))))
                    .build();

            saveUser(modifiedUser);

            return ServerStatus.PASSWORD_UPDATE;
        } catch(Exception e) {
            return ServerStatus.PASSWORD_UPDATE_ERROR;
        }
    }
}