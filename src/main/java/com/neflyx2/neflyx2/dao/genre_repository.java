package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface genre_repository extends JpaRepository<genre, Integer> {
}
