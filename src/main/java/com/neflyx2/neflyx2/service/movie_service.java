package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.movie_dao;
import com.neflyx2.neflyx2.model.dto.MovieListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class movie_service {

    private static final Logger logger = LoggerFactory.getLogger(movie_service.class);

    @Autowired
    movie_dao movie_dao;

    public Page<MovieListDTO> get_filtered_movies(String title, Integer year, String genre, String director, int page, int size) {
        long startTime = System.currentTimeMillis();

        logger.info("========================================");
        logger.info("Starting search with filters:");
        logger.info("Title: {}", title);
        logger.info("Year: {}", year);
        logger.info("Genre: {}", genre);
        logger.info("Director: {}", director);
        logger.info("Page: {}, Size: {}", page, size);
        logger.info("========================================");

        // Normalizar parámetros
        String titleFilter = (title != null && !title.trim().isEmpty()) ? title.trim() : null;
        String genreFilter = (genre != null && !genre.trim().isEmpty()) ? genre.trim() : null;
        String directorFilter = (director != null && !director.trim().isEmpty()) ? director.trim() : null;

        Pageable pageable = PageRequest.of(page, size);

        boolean hasTitle = titleFilter != null;
        boolean hasYear = year != null;
        boolean hasGenre = genreFilter != null;
        boolean hasDirector = directorFilter != null;

        Page<MovieListDTO> result = null;
        String queryType = "";

        try {
            // Sin filtros
            if (!hasTitle && !hasYear && !hasGenre && !hasDirector) {
                queryType = "findAllMovies";
                logger.info("Executing: {}", queryType);
                long queryStart = System.currentTimeMillis();
                result = movie_dao.findAllMovies(pageable);
                logger.info("{} took: {}ms", queryType, (System.currentTimeMillis() - queryStart));
            }
            // Un solo filtro
            else if (hasTitle && !hasYear && !hasGenre && !hasDirector) {
                queryType = "findByTitle";
                logger.info("Executing: {} with title='{}'", queryType, titleFilter);
                long queryStart = System.currentTimeMillis();
                result = movie_dao.findByTitle(titleFilter, pageable);
                logger.info("{} took: {}ms", queryType, (System.currentTimeMillis() - queryStart));
            }
            else if (!hasTitle && hasYear && !hasGenre && !hasDirector) {
                queryType = "findByYear";
                logger.info("Executing: {} with year={}", queryType, year);
                long queryStart = System.currentTimeMillis();
                result = movie_dao.findByYear(year, pageable);
                logger.info("{} took: {}ms", queryType, (System.currentTimeMillis() - queryStart));
            }
            else if (!hasTitle && !hasYear && hasGenre && !hasDirector) {
                queryType = "findByGenre";
                logger.info("Executing: {} with genre='{}'", queryType, genreFilter);
                long queryStart = System.currentTimeMillis();
                result = movie_dao.findByGenre(genreFilter, pageable);
                logger.info("{} took: {}ms", queryType, (System.currentTimeMillis() - queryStart));
            }
            else if (!hasTitle && !hasYear && !hasGenre && hasDirector) {
                queryType = "findByDirector";
                logger.info("Executing: {} with director='{}'", queryType, directorFilter);
                long queryStart = System.currentTimeMillis();
                result = movie_dao.findByDirector(directorFilter, pageable);
                logger.info("{} took: {}ms", queryType, (System.currentTimeMillis() - queryStart));
            }
            // Dos filtros
            else if (hasTitle && hasYear && !hasGenre && !hasDirector) {
                queryType = "findByTitleAndYear";
                logger.info("Executing: {} with title='{}', year={}", queryType, titleFilter, year);
                long queryStart = System.currentTimeMillis();
                result = movie_dao.findByTitleAndYear(titleFilter, year, pageable);
                logger.info("{} took: {}ms", queryType, (System.currentTimeMillis() - queryStart));
            }
            else if (hasTitle && !hasYear && hasGenre && !hasDirector) {
                queryType = "findByTitleAndGenre";
                logger.info("Executing: {} with title='{}', genre='{}'", queryType, titleFilter, genreFilter);
                long queryStart = System.currentTimeMillis();
                result = movie_dao.findByTitleAndGenre(titleFilter, genreFilter, pageable);
                logger.info("{} took: {}ms", queryType, (System.currentTimeMillis() - queryStart));
            }
            // Fallback para casos complejos
            else {
                queryType = "findMoviesForList (FALLBACK)";
                logger.info("Executing: {} with multiple filters", queryType);
                long queryStart = System.currentTimeMillis();
                result = movie_dao.findMoviesForList(titleFilter, year, genreFilter, directorFilter, pageable);
                logger.info("{} took: {}ms", queryType, (System.currentTimeMillis() - queryStart));
            }

            long totalTime = System.currentTimeMillis() - startTime;

            logger.info("========================================");
            logger.info("RESULTS:");
            logger.info("Query type: {}", queryType);
            logger.info("Total elements found: {}", result != null ? result.getTotalElements() : "NULL");
            logger.info("Total pages: {}", result != null ? result.getTotalPages() : "NULL");
            logger.info("Current page: {}", result != null ? result.getNumber() : "NULL");
            logger.info("Elements in page: {}", result != null ? result.getNumberOfElements() : "NULL");
            logger.info("TOTAL EXECUTION TIME: {}ms", totalTime);
            logger.info("========================================");

            return result;

        } catch (Exception e) {
            long totalTime = System.currentTimeMillis() - startTime;
            logger.error("========================================");
            logger.error("ERROR in query: {}", queryType);
            logger.error("Time before error: {}ms", totalTime);
            logger.error("Error message: {}", e.getMessage());
            logger.error("Error type: {}", e.getClass().getName());
            logger.error("========================================");
            e.printStackTrace();
            throw e;
        }
    }
}