package org.gycoding.accounts.model.database;

import org.gycoding.accounts.model.entities.Email;
import org.gycoding.accounts.model.entities.ServerState;
import org.gycoding.accounts.model.entities.User;
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

    public User saveUsuario(User usuario) {
        return accountRepository.save(usuario);
    }

    public void deleteUsuario(Integer id) {
        accountRepository.deleteById(id);
    }


    /* ===============# Custom Methods #=============== */

    public Boolean checkLogin(String username, String password) {
        return true;
    }

    public Boolean checkLogin(Email email, String password) {
        return true;
    }

    public ServerState signUp(User user, String password) {
        return ServerState.SUCCESS;
    }

    public String getSession(String username, String password) {
        return null;
    }
}