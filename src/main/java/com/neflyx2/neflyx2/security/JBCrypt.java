package com.neflyx2.neflyx2.security;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class JBCrypt {
    private static final int COST_FACTOR = 12;
    public String hasher(String password){
        String salts = BCrypt.gensalt(COST_FACTOR);
        return BCrypt.hashpw(password,salts);
    }
    public boolean encoder(String cadidate_password, String hashed_password) {
        return BCrypt.checkpw(cadidate_password, hashed_password);
    }

}
