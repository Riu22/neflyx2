package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface user_dao extends JpaRepository<user, Integer> {
    user findByUsername(String username);
}
