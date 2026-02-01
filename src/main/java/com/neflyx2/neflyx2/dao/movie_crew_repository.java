package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.movie_crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface movie_crew_repository extends JpaRepository<movie_crew, Integer> {
}
