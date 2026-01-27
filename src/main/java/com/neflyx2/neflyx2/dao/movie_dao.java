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

    // Query básica - SIN genres ni directors
    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            NULL AS genres,
            NULL AS directors
        FROM movie m 
        ORDER BY m.release_date DESC
        """,
            countQuery = "SELECT COUNT(*) FROM movie",
            nativeQuery = true)
    Page<MovieListDTO> findAllMovies(Pageable pageable);

    // Query por título
    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            NULL AS genres,
            NULL AS directors
        FROM movie m 
        WHERE m.title LIKE CONCAT('%', :title, '%')
        ORDER BY m.release_date DESC
        """,
            countQuery = """
        SELECT COUNT(*) FROM movie m 
        WHERE m.title LIKE CONCAT('%', :title, '%')
        """,
            nativeQuery = true)
    Page<MovieListDTO> findByTitle(@Param("title") String title, Pageable pageable);

    // Query por año
    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            NULL AS genres,
            NULL AS directors
        FROM movie m 
        WHERE YEAR(m.release_date) = :year
        ORDER BY m.release_date DESC
        """,
            countQuery = """
        SELECT COUNT(*) FROM movie m 
        WHERE YEAR(m.release_date) = :year
        """,
            nativeQuery = true)
    Page<MovieListDTO> findByYear(@Param("year") Integer year, Pageable pageable);

    // Query por género - SIMPLE Y RÁPIDA
    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            NULL AS genres,
            NULL AS directors
        FROM movie m 
        INNER JOIN movie_genres mg ON m.movie_id = mg.movie_id 
        INNER JOIN genre g ON mg.genre_id = g.genre_id 
        WHERE g.genre_name = :genreName
        ORDER BY m.release_date DESC
        """,
            countQuery = """
        SELECT COUNT(DISTINCT m.movie_id) 
        FROM movie m 
        INNER JOIN movie_genres mg ON m.movie_id = mg.movie_id 
        INNER JOIN genre g ON mg.genre_id = g.genre_id 
        WHERE g.genre_name = :genreName
        """,
            nativeQuery = true)
    Page<MovieListDTO> findByGenre(@Param("genreName") String genreName, Pageable pageable);

    // Query por director - SIMPLE Y RÁPIDA
    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            NULL AS genres,
            NULL AS directors
        FROM movie m 
        INNER JOIN movie_crew mc ON m.movie_id = mc.movie_id 
        INNER JOIN person p ON mc.person_id = p.person_id
        WHERE mc.job = 'Director' 
        AND p.person_name LIKE CONCAT('%', :director, '%')
        ORDER BY m.release_date DESC
        """,
            countQuery = """
        SELECT COUNT(DISTINCT m.movie_id) 
        FROM movie m 
        INNER JOIN movie_crew mc ON m.movie_id = mc.movie_id 
        INNER JOIN person p ON mc.person_id = p.person_id
        WHERE mc.job = 'Director' 
        AND p.person_name LIKE CONCAT('%', :director, '%')
        """,
            nativeQuery = true)
    Page<MovieListDTO> findByDirector(@Param("director") String director, Pageable pageable);

    // Query combinada: título + año
    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            NULL AS genres,
            NULL AS directors
        FROM movie m 
        WHERE m.title LIKE CONCAT('%', :title, '%')
        AND YEAR(m.release_date) = :year
        ORDER BY m.release_date DESC
        """,
            countQuery = """
        SELECT COUNT(*) FROM movie m 
        WHERE m.title LIKE CONCAT('%', :title, '%')
        AND YEAR(m.release_date) = :year
        """,
            nativeQuery = true)
    Page<MovieListDTO> findByTitleAndYear(@Param("title") String title,
                                          @Param("year") Integer year,
                                          Pageable pageable);

    // Query combinada: título + género
    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            NULL AS genres,
            NULL AS directors
        FROM movie m 
        INNER JOIN movie_genres mg ON m.movie_id = mg.movie_id 
        INNER JOIN genre g ON mg.genre_id = g.genre_id 
        WHERE m.title LIKE CONCAT('%', :title, '%')
        AND g.genre_name = :genreName
        ORDER BY m.release_date DESC
        """,
            countQuery = """
        SELECT COUNT(DISTINCT m.movie_id) 
        FROM movie m 
        INNER JOIN movie_genres mg ON m.movie_id = mg.movie_id 
        INNER JOIN genre g ON mg.genre_id = g.genre_id 
        WHERE m.title LIKE CONCAT('%', :title, '%')
        AND g.genre_name = :genreName
        """,
            nativeQuery = true)
    Page<MovieListDTO> findByTitleAndGenre(@Param("title") String title,
                                           @Param("genreName") String genreName,
                                           Pageable pageable);

    // Fallback SIMPLIFICADO para múltiples filtros
    @Query(value = """
        SELECT DISTINCT
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            NULL AS genres,
            NULL AS directors
        FROM movie m 
        LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id
        LEFT JOIN genre g ON mg.genre_id = g.genre_id
        LEFT JOIN movie_crew mc ON m.movie_id = mc.movie_id AND mc.job = 'Director'
        LEFT JOIN person p ON mc.person_id = p.person_id
        WHERE (:title IS NULL OR m.title LIKE CONCAT('%', :title, '%')) 
          AND (:year IS NULL OR YEAR(m.release_date) = :year) 
          AND (:genreName IS NULL OR :genreName = '' OR g.genre_name = :genreName) 
          AND (:director IS NULL OR :director = '' OR p.person_name LIKE CONCAT('%', :director, '%'))
        ORDER BY m.release_date DESC
        """,
            countQuery = """
        SELECT COUNT(DISTINCT m.movie_id) 
        FROM movie m 
        LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id
        LEFT JOIN genre g ON mg.genre_id = g.genre_id
        LEFT JOIN movie_crew mc ON m.movie_id = mc.movie_id AND mc.job = 'Director'
        LEFT JOIN person p ON mc.person_id = p.person_id
        WHERE (:title IS NULL OR m.title LIKE CONCAT('%', :title, '%')) 
          AND (:year IS NULL OR YEAR(m.release_date) = :year) 
          AND (:genreName IS NULL OR :genreName = '' OR g.genre_name = :genreName) 
          AND (:director IS NULL OR :director = '' OR p.person_name LIKE CONCAT('%', :director, '%'))
        """,
            nativeQuery = true)
    Page<MovieListDTO> findMoviesForList(@Param("title") String title,
                                         @Param("year") Integer year,
                                         @Param("genreName") String genreName,
                                         @Param("director") String director,
                                         Pageable pageable);
}