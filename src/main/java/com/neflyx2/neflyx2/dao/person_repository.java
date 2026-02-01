package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface person_repository extends JpaRepository<person, Integer> {
}
