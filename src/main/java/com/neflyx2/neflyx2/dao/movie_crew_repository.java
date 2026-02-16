package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.movie_crew;
import com.neflyx2.neflyx2.model.entiti.movie_crew_id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface movie_crew_repository extends JpaRepository<movie_crew, movie_crew_id> {

    @Query("SELECT mcr FROM movie_crew mcr WHERE mcr.movie.movie_id = :movieId")
    Page<movie_crew> findByMovieId(Integer movieId, Pageable pageable);
}
