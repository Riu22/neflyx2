package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface country_repository extends JpaRepository<country, Integer> {
}
