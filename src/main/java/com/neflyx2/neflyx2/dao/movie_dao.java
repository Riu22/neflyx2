package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.dto.MovieListDTO;
import com.neflyx2.neflyx2.model.entiti.movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface movie_dao extends JpaRepository<movie, Integer> {

    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage, 
            (SELECT GROUP_CONCAT(g.genre_name ORDER BY g.genre_name SEPARATOR ', ')
             FROM movie_genres mg 
             JOIN genre g ON mg.genre_id = g.genre_id 
             WHERE mg.movie_id = m.movie_id) AS genres,
            (SELECT GROUP_CONCAT(p.person_name ORDER BY p.person_name SEPARATOR ', ')
             FROM movie_crew mc 
             JOIN person p ON mc.person_id = p.person_id 
             WHERE mc.movie_id = m.movie_id AND mc.job = 'Director') AS directors 
        FROM movie m 
        WHERE (:title IS NULL OR m.title LIKE CONCAT('%', :title, '%')) 
          AND (:year IS NULL OR YEAR(m.release_date) = :year) 
          AND (:genreName IS NULL OR :genreName = '' OR 
               m.movie_id IN (SELECT mg2.movie_id 
                              FROM movie_genres mg2 
                              JOIN genre g2 ON mg2.genre_id = g2.genre_id 
                              WHERE g2.genre_name = :genreName)) 
          AND (:director IS NULL OR :director = '' OR 
               m.movie_id IN (SELECT mc2.movie_id 
                              FROM movie_crew mc2 
                              JOIN person p2 ON mc2.person_id = p2.person_id 
                              WHERE mc2.job = 'Director' 
                              AND p2.person_name LIKE CONCAT('%', :director, '%'))) 
        ORDER BY m.release_date DESC
        """,
            countQuery = """
        SELECT COUNT(*) FROM movie m 
        WHERE (:title IS NULL OR m.title LIKE CONCAT('%', :title, '%')) 
          AND (:year IS NULL OR YEAR(m.release_date) = :year) 
          AND (:genreName IS NULL OR :genreName = '' OR 
               m.movie_id IN (SELECT mg2.movie_id FROM movie_genres mg2 
                              JOIN genre g2 ON mg2.genre_id = g2.genre_id 
                              WHERE g2.genre_name = :genreName)) 
          AND (:director IS NULL OR :director = '' OR 
               m.movie_id IN (SELECT mc2.movie_id FROM movie_crew mc2 
                              JOIN person p2 ON mc2.person_id = p2.person_id 
                              WHERE mc2.job = 'Director' AND p2.person_name LIKE CONCAT('%', :director, '%')))
        """,
            nativeQuery = true)
    Page<MovieListDTO> findMoviesForList(@Param("title") String title,
                                         @Param("year") Integer year,
                                         @Param("genreName") String genreName,
                                         @Param("director") String director,
                                         Pageable pageable);
}