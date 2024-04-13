package org.gycoding.accountsv2.infrastructure.adapter.database;

import org.gycoding.accountsv2.domain.model.*;
import org.gycoding.accountsv2.infrastructure.adapter.database.jpa.AccountJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountJpaRepository accountRepository;

    @Autowired
    public AccountService(AccountJpaRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<UserMO> getUsuarios() {
        return accountRepository.findAll();
    }

    public UserMO getUsuario(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    public UserMO getUsuario(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    public UserMO getUsuario(Email email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    public UserMO saveUsuario(UserMO user) {
        return accountRepository.save(user);
    }

    public void deleteUsuario(Integer id) {
        accountRepository.deleteById(id);
    }
}