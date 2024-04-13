package org.gycoding.accountsv2.application.service;

import org.gycoding.accountsv2.domain.model.GYToken;
import org.gycoding.accountsv2.domain.model.SessionMO;
import org.gycoding.accountsv2.domain.model.UserMO;

import org.gycoding.accountsv2.domain.port.AccountUsecases;

import org.gycoding.accountsv2.infrastructure.adapter.cipher.ByteHexConverter;
import org.gycoding.accountsv2.infrastructure.adapter.cipher.Cipher;
import org.gycoding.accountsv2.infrastructure.adapter.jwt.JWTService;

import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountUsecases {
    @Override
    public Boolean checkLogin(UserMO user) {
        if(user != null) {
            if(Cipher.verifyPassword(user.password(), ByteHexConverter.hexToBytes(user.salt()), ByteHexConverter.hexToBytes(user.password()))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public UserMO signUp(UserMO user) {
        if(this.checkLogin(user)) {
            return null;
        } else {
            UserMO.builder()
                    .username(user.username())
                    .email(user.email())
                    .salt(ByteHexConverter.bytesToHex(Cipher.generateSalt()))
                    .password(ByteHexConverter.bytesToHex(Cipher.hashPassword(user.password(), ByteHexConverter.hexToBytes(user.salt()))))
                    .token(new GYToken(Cipher.generateToken()));

            return user;
        }
    }

    @Override
    public SessionMO getSession(UserMO user) {
        if(this.checkLogin(user)) {
            return new SessionMO(user, JWTService.generateToken(user.username()));
        } else {
            return null;
        }
    }
}
