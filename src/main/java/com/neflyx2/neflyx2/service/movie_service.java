package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.movie_dao;
import com.neflyx2.neflyx2.model.dto.MovieListDTO;
import com.neflyx2.neflyx2.model.dto.MovieSearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class movie_service {

    private static final Logger logger = LoggerFactory.getLogger(movie_service.class);

    @Autowired
    movie_dao movie_dao;


    public Page<MovieListDTO> get_filtered_movies(String title, Integer year, String genre, String director, int page, int size) {
       MovieSearchCriteria criteria = new MovieSearchCriteria(title, year, genre, director, PageRequest.of(page, size));
       logger.info("Buscando movies con filtros: {}", criteria);
       long start_time = System.currentTimeMillis();
       Page<MovieListDTO> result = execute_search(criteria);
       logger.info("Encontrados{} en {}ms", result.getTotalElements(), System.currentTimeMillis() - start_time);
       return result;
    }

    private Page<MovieListDTO> execute_search(MovieSearchCriteria criteria) {
        if (criteria.has_no_filters()) {
            return movie_dao.findAllMovies(criteria.pageable());
        }
        if (criteria.get_active_filters_count() == 1) {
            return execute_simple_filter(criteria);
        }
        if (criteria.get_active_filters_count() == 2) {
            return execute_double_filter(criteria);
        }
        return movie_dao.findMoviesForList(
                criteria.title(),
                criteria.year(),
                criteria.genre(),
                criteria.director(),
                criteria.pageable()
        );
    }

    private Page<MovieListDTO> execute_simple_filter(MovieSearchCriteria criteria) {
        if (criteria.has_title()) {
            logger.debug("Ejecutando búsqueda por título");
            return movie_dao.findByTitle(criteria.title(), criteria.pageable());
        }
        if (criteria.has_year()) {
            logger.debug("Ejecutando busqueda por año");
            return movie_dao.findByYear(criteria.year(), criteria.pageable());
        }
        if (criteria.has_genre()) {
            logger.debug("Ejecutando búsqueda por género");
            return movie_dao.findByGenre(criteria.genre(), criteria.pageable());
        }
        if (criteria.has_director()) {
            logger.debug("Ejecutando búsqueda por director");
            return movie_dao.findByDirector(criteria.director(), criteria.pageable());
        }
        return Page.empty();
    }

    private Page<MovieListDTO> execute_double_filter(MovieSearchCriteria criteria) {
        if (criteria.has_title() && criteria.has_genre()) {
            logger.debug("Ejecutando búsqueda por título y género");
            return movie_dao.findByTitleAndGenre(criteria.title(), criteria.genre(), criteria.pageable());
        }
        if (criteria.has_title() && criteria.has_year()) {
            logger.debug("buscando por titulo y año");
            return movie_dao.findByTitleAndYear(criteria.title(), criteria.year(), criteria.pageable());
        }
        logger.debug("Executando el fallback");
        return movie_dao.findMoviesForList(
                criteria.title(),
                criteria.year(),
                criteria.genre(),
                criteria.director(),
                criteria.pageable()
        );
    }

}

