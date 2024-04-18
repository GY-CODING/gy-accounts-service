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

    public List<User> getUsuarios() {
        return accountRepository.findAll();
    }

    public User getUsuario(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    public User getUsuario(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    public User getUsuario(Email email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    public User saveUsuario(User usuario) {
        return accountRepository.save(usuario);
    }

    public void deleteUsuario(Integer id) {
        accountRepository.deleteById(id);
    }


    /* ===============# Custom Methods #=============== */

    public Boolean checkLogin(String username, String password) {
        User user = getUsuario(username);

        if(user != null) {
            if(Cipher.verifyPassword(password, ByteHexConverter.hexToBytes(user.getSalt()), ByteHexConverter.hexToBytes(user.getPassword()))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean checkLogin(Email email, String password) {
        User user = getUsuario(email);

        if(user != null) {
            if(Cipher.verifyPassword(password, ByteHexConverter.hexToBytes(user.getSalt()), ByteHexConverter.hexToBytes(user.getPassword()))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ServerStatus signUp(User user, String password) {
        if(this.checkLogin(user.getUsername(), password)) {
            return ServerStatus.INVALID_SIGNUP;
        } else {
            user.setSalt(ByteHexConverter.bytesToHex(Cipher.generateSalt()));
            user.setPassword(ByteHexConverter.bytesToHex(Cipher.hashPassword(password, ByteHexConverter.hexToBytes(user.getSalt()))));
            user.setToken(new GYToken(Cipher.generateToken()));

            saveUsuario(user);

            return ServerStatus.SUCCESS;
        }
    }

    public Session getSession(String username, String password) {
        if(this.checkLogin(username, password)) {
            return new Session(this.getUsuario(username));
        } else {
            return null;
        }
    }

    public Session getSession(Email email, String password) {
        if(this.checkLogin(email, password)) {
            return new Session(this.getUsuario(email));
        } else {
            return null;
        }
    }

    public ServerStatus updateUsername(String username, String password, String newUsername) {
        if(this.checkLogin(username, password)) {
            User user = getUsuario(username);

            if(user != null) {
                user.setUsername(newUsername);

                saveUsuario(user);

                return ServerStatus.SUCCESS;
            } else {
                return ServerStatus.INVALID_USERNAME;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updateUsername(Email email, String password, String newUsername) {
        if(this.checkLogin(email, password)) {
            User user = getUsuario(email);

            if(user != null) {
                user.setUsername(newUsername);

                saveUsuario(user);

                return ServerStatus.SUCCESS;
            } else {
                return ServerStatus.INVALID_USERNAME;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updateEmail(String username, String password, Email newEmail) {
        if(this.checkLogin(username, password)) {
            User user = getUsuario(username);

            if(user != null) {
                user.setEmail(newEmail);

                saveUsuario(user);

                return ServerStatus.SUCCESS;
            } else {
                return ServerStatus.INVALID_USERNAME;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updateEmail(Email email, String password, Email newEmail) {
        if(this.checkLogin(email, password)) {
            User user = getUsuario(email);

            if(user != null) {
                user.setEmail(newEmail);

                saveUsuario(user);

                return ServerStatus.SUCCESS;
            } else {
                return ServerStatus.INVALID_USERNAME;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updatePassword(String username, String password, String newPassword) {
        if(this.checkLogin(username, password)) {
            User user = getUsuario(username);

            if(user != null) {
                user.setSalt(ByteHexConverter.bytesToHex(Cipher.generateSalt()));
                user.setPassword(ByteHexConverter.bytesToHex(Cipher.hashPassword(newPassword, ByteHexConverter.hexToBytes(user.getSalt()))));

                saveUsuario(user);

                return ServerStatus.SUCCESS;
            } else {
                return ServerStatus.INVALID_USERNAME;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updatePassword(Email email, String password, String newPassword) {
        if(this.checkLogin(email, password)) {
            User user = getUsuario(email);

            if(user != null) {
                user.setSalt(ByteHexConverter.bytesToHex(Cipher.generateSalt()));
                user.setPassword(ByteHexConverter.bytesToHex(Cipher.hashPassword(newPassword, ByteHexConverter.hexToBytes(user.getSalt()))));

                saveUsuario(user);

                return ServerStatus.SUCCESS;
            } else {
                return ServerStatus.INVALID_USERNAME;
            }
        } else {
            return ServerStatus.INVALID_LOGIN;
        }
    }

    public ServerStatus updatePasswordForgotten(String username, String newPassword) {
        User user = getUsuario(username);

        if(user != null) {
            user.setSalt(ByteHexConverter.bytesToHex(Cipher.generateSalt()));
            user.setPassword(ByteHexConverter.bytesToHex(Cipher.hashPassword(newPassword, ByteHexConverter.hexToBytes(user.getSalt()))));

            saveUsuario(user);

            return ServerStatus.SUCCESS;
        } else {
            return ServerStatus.INVALID_USERNAME;
        }
    }

    public ServerStatus updatePasswordForgotten(Email email, String newPassword) {
        User user = getUsuario(email);

        if(user != null) {
            user.setSalt(ByteHexConverter.bytesToHex(Cipher.generateSalt()));
            user.setPassword(ByteHexConverter.bytesToHex(Cipher.hashPassword(newPassword, ByteHexConverter.hexToBytes(user.getSalt()))));

            saveUsuario(user);

            return ServerStatus.SUCCESS;
        } else {
            return ServerStatus.INVALID_USERNAME;
        }
    }
}