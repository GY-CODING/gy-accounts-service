package org.gycoding.accounts.model.database;

import org.gycoding.accounts.model.entities.*;
import org.gycoding.accounts.model.util.ByteHexConverter;
import org.gycoding.accounts.model.util.Cipher;
import org.gycoding.accounts.model.util.JWTService;
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

    public ServerState signUp(User user, String password) {
        if(this.checkLogin(user.getUsername(), password)) {
            return ServerState.INVALID_SIGNUP;
        } else {
            user.setSalt(ByteHexConverter.bytesToHex(Cipher.generateSalt()));
            user.setPassword(ByteHexConverter.bytesToHex(Cipher.hashPassword(password, ByteHexConverter.hexToBytes(user.getSalt()))));
            user.setToken(new GYToken(Cipher.generateToken()));

            saveUsuario(user);

            return ServerState.SUCCESS;
        }
    }

    public Session getSession(String username, String password) {
        if(this.checkLogin(username, password)) {
            return new Session(this.getUsuario(username), JWTService.generateToken(username));
        } else {
            return null;
        }
    }

    public Session getSession(Email email, String password) {
        if(this.checkLogin(email, password)) {
            return new Session(this.getUsuario(email), JWTService.generateToken(email));
        } else {
            return null;
        }
    }
}