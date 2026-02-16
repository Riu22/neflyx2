package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.user_dao;
import com.neflyx2.neflyx2.model.entiti.user;
import com.neflyx2.neflyx2.security.JBCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class user_service {
    @Autowired
    user_dao user_dao;
    @Autowired
    JBCrypt bcrypter;

    public boolean login(String username, String password) {
        user user = user_dao.findByUsername(username);
        if (user != null) {
            return bcrypter.encoder(password, user.getPassword());
        }
        return false;
    }

    public boolean register(String username, String password, String email) {
        if (user_dao.findByUsername(username) != null) {
            return false;
        }
        String encript = bcrypter.hasher(password);
        user user = new user(username, encript, email);
        user_dao.save(user);
        return true;
    }
}