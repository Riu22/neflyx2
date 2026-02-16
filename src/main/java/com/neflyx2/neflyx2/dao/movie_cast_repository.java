package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.movie_cast;
import com.neflyx2.neflyx2.model.entiti.movie_cast_id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface movie_cast_repository extends JpaRepository<movie_cast, movie_cast_id> {

    @Query("SELECT mc FROM movie_cast mc WHERE mc.movie.movie_id = :movieId")
    Page<movie_cast> findByMovieId(Integer movieId, Pageable pageable);
}
