package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.movie_cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface movie_cast_repository  extends JpaRepository<movie_cast, Integer> {
}
