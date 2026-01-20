package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface movie_dao extends JpaRepository<movie, Integer> {
    List<movie> findByTitleContainingIgnoreCase(String title);
    //preguntar a pere si se puede utilizar criteria
}
