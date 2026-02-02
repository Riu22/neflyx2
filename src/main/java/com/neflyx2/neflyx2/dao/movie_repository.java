package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.dto.MovieDTO;
import com.neflyx2.neflyx2.model.dto.MovieListDTO;
import com.neflyx2.neflyx2.model.entiti.movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface movie_repository extends JpaRepository<movie, Integer> {

    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            GROUP_CONCAT(DISTINCT g.genre_name ORDER BY g.genre_name SEPARATOR ', ') AS genres,
            NULL AS directors
        FROM movie m 
        LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id
        LEFT JOIN genre g ON mg.genre_id = g.genre_id
        GROUP BY m.movie_id, m.title, m.release_date, m.vote_average
        ORDER BY m.release_date DESC
        """,
            countQuery = "SELECT COUNT(*) FROM movie",
            nativeQuery = true)
    Page<MovieListDTO> findAllMovies(Pageable pageable);

    @Query(value = "SELECT movie_id FROM movie WHERE title LIKE CONCAT('%', :title, '%')",
            nativeQuery = true)
    List<Integer> findIdsByTitle(@Param("title") String title);

    @Query(value = "SELECT movie_id FROM movie WHERE YEAR(release_date) = :year",
            nativeQuery = true)
    List<Integer> findIdsByYear(@Param("year") Integer year);

    @Query(value = """
        SELECT DISTINCT m.movie_id 
        FROM movie m 
        INNER JOIN movie_genres mg ON m.movie_id = mg.movie_id 
        INNER JOIN genre g ON mg.genre_id = g.genre_id 
        WHERE g.genre_name = :genreName
        """,
            nativeQuery = true)
    List<Integer> findIdsByGenre(@Param("genreName") String genreName);

    @Query(value = """
    SELECT DISTINCT m.movie_id 
    FROM movie m 
    JOIN movie_crew mc ON m.movie_id = mc.movie_id 
    JOIN person p ON mc.person_id = p.person_id
    WHERE mc.job = 'Director' 
    AND p.person_name LIKE CONCAT('%', :director, '%')
    """, nativeQuery = true)
    List<Integer> findIdsByDirector(@Param("director") String director);

    @Query(value = """
    SELECT DISTINCT m.movie_id 
    FROM movie m 
    JOIN movie_cast mca ON m.movie_id = mca.movie_id 
    JOIN person p ON mca.person_id = p.person_id
    WHERE p.person_name LIKE CONCAT('%', :actor, '%')
    """, nativeQuery = true)
    List<Integer> findIdsByActor(@Param("actor") String actor);

    @Query(value = """
        SELECT 
            m.movie_id AS movieId, 
            m.title AS title, 
            m.release_date AS releaseDate, 
            m.vote_average AS voteAverage,
            GROUP_CONCAT(DISTINCT g.genre_name ORDER BY g.genre_name SEPARATOR ', ') AS genres,
            NULL AS directors
        FROM movie m 
        LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id
        LEFT JOIN genre g ON mg.genre_id = g.genre_id
        WHERE m.movie_id IN :ids
        GROUP BY m.movie_id, m.title, m.release_date, m.vote_average
        ORDER BY m.release_date DESC
        """,
            countQuery = "SELECT COUNT(*) FROM movie WHERE movie_id IN :ids",
            nativeQuery = true)
    Page<MovieListDTO> findMoviesByIds(@Param("ids") List<Integer> ids, Pageable pageable);

    @Query(value = """
    SELECT 
        movie_id, title, budget, homepage, overview, 
        popularity, release_date, revenue, runtime, 
        movie_status, tagline, vote_average, vote_count
    FROM movie 
    WHERE movie_id = :id
    """, nativeQuery = true)
    Optional<MovieDTO> findMovieById(@Param("id") Integer id);

    @Query(value = """
    SELECT DISTINCT movie_id 
    FROM movie_cast 
    WHERE character_name LIKE CONCAT('%', :character, '%')
    """, nativeQuery = true)
    List<Integer> findIdsByCharacter(@Param("character") String character);
}