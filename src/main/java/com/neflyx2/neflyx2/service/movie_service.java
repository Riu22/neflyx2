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

import java.util.*;

@Service
public class movie_service {

    private static final Logger logger = LoggerFactory.getLogger(movie_service.class);

    @Autowired
    movie_dao movie_dao;

    public Page<MovieListDTO> get_filtered_movies(String title, Integer year, String genre,
                                                  String actor, String director, int page, int size) {
        MovieSearchCriteria criteria = new MovieSearchCriteria(
                title, year, genre, director, actor, PageRequest.of(page, size)
        );

        logger.info("Buscando movies con filtros: {}", criteria);
        long start_time = System.currentTimeMillis();

        Page<MovieListDTO> result = execute_search(criteria);

        logger.info("Encontrados {} en {}ms",
                result.getTotalElements(),
                System.currentTimeMillis() - start_time);

        return result;
    }

    private Page<MovieListDTO> execute_search(MovieSearchCriteria criteria) {
        // Sin filtros - devuelve todas
        if (criteria.has_no_filters()) {
            return movie_dao.findAllMovies(criteria.pageable());
        }

        // Obtener IDs que cumplen cada filtro
        List<Set<Integer>> filter_results = new ArrayList<>();

        if (criteria.has_title()) {
            Set<Integer> title_ids = new HashSet<>(movie_dao.findIdsByTitle(criteria.title()));
            filter_results.add(title_ids);
            logger.debug("Películas con título '{}': {}", criteria.title(), title_ids.size());
        }

        if (criteria.has_year()) {
            Set<Integer> year_ids = new HashSet<>(movie_dao.findIdsByYear(criteria.year()));
            filter_results.add(year_ids);
            logger.debug("Películas del año {}: {}", criteria.year(), year_ids.size());
        }

        if (criteria.has_genre()) {
            Set<Integer> genre_ids = new HashSet<>(movie_dao.findIdsByGenre(criteria.genre()));
            filter_results.add(genre_ids);
            logger.debug("Películas del género '{}': {}", criteria.genre(), genre_ids.size());
        }

        if (criteria.has_director()) {
            Set<Integer> director_ids = new HashSet<>(movie_dao.findIdsByDirector(criteria.director()));
            filter_results.add(director_ids);
            logger.debug("Películas del director '{}': {}", criteria.director(), director_ids.size());
        }

        if (criteria.has_actor()) {
            Set<Integer> actor_ids = new HashSet<>(movie_dao.findIdsByActor(criteria.actor()));
            filter_results.add(actor_ids);
            logger.debug("Películas del actor '{}': {}", criteria.actor(), actor_ids.size());
        }

        // Intersección de todos los sets (AND lógico)
        Set<Integer> final_ids = intersect_all(filter_results);

        logger.info("IDs finales después de aplicar {} filtros: {}",
                filter_results.size(), final_ids.size());

        // Si no hay resultados, devolver página vacía
        if (final_ids.isEmpty()) {
            return Page.empty(criteria.pageable());
        }

        // Convertir Set a List para la query
        List<Integer> ids_list = new ArrayList<>(final_ids);

        // Obtener las películas completas con paginación
        return movie_dao.findMoviesByIds(ids_list, criteria.pageable());
    }


    private Set<Integer> intersect_all(List<Set<Integer>> sets) {
        if (sets.isEmpty()) {
            return new HashSet<>();
        }

        // Empezar con el primer set
        Set<Integer> result = new HashSet<>(sets.get(0));

        // Hacer intersección con cada set restante
        for (int i = 1; i < sets.size(); i++) {
            result.retainAll(sets.get(i));
        }

        return result;
    }
}