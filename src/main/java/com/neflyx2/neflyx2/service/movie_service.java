package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.movie_repository;
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
    movie_repository movie_repository;

    public Page<MovieListDTO> get_filtered_movies(String title, Integer year, String genre,
                                                  String actor, String director, String character, int page, int size) {

        // CORRECCIÓN: El orden debe coincidir EXACTAMENTE con el Record:
        // title, year, genre, director, actor, character
        MovieSearchCriteria criteria = new MovieSearchCriteria(
                title, year, genre, director, actor, character, PageRequest.of(page, size)
        );

        logger.info("Buscando con criterios: {}", criteria);
        return execute_search(criteria);
    }

    private Page<MovieListDTO> execute_search(MovieSearchCriteria criteria) {
        if (criteria.has_no_filters()) {
            return movie_repository.findAllMovies(criteria.pageable());
        }

        List<Set<Integer>> filter_results = new ArrayList<>();

        // Función auxiliar para no repetir código y validar si el filtro vacía el resultado
        if (criteria.has_title()) {
            Set<Integer> ids = new HashSet<>(movie_repository.findIdsByTitle(criteria.title()));
            if (ids.isEmpty()) return Page.empty(criteria.pageable()); // Si un filtro da 0, el total es 0
            filter_results.add(ids);
        }

        if (criteria.has_year()) {
            Set<Integer> ids = new HashSet<>(movie_repository.findIdsByYear(criteria.year()));
            if (ids.isEmpty()) return Page.empty(criteria.pageable());
            filter_results.add(ids);
        }

        if (criteria.has_genre()) {
            Set<Integer> ids = new HashSet<>(movie_repository.findIdsByGenre(criteria.genre()));
            if (ids.isEmpty()) return Page.empty(criteria.pageable());
            filter_results.add(ids);
        }

        if (criteria.has_director()) {
            Set<Integer> ids = new HashSet<>(movie_repository.findIdsByDirector(criteria.director()));
            if (ids.isEmpty()) return Page.empty(criteria.pageable());
            filter_results.add(ids);
        }

        if (criteria.has_actor()) {
            Set<Integer> ids = new HashSet<>(movie_repository.findIdsByActor(criteria.actor()));
            if (ids.isEmpty()) return Page.empty(criteria.pageable());
            filter_results.add(ids);
        }

        if (criteria.has_character()) {
            Set<Integer> ids = new HashSet<>(movie_repository.findIdsByCharacter(criteria.character()));
            if (ids.isEmpty()) return Page.empty(criteria.pageable());
            filter_results.add(ids);
        }

        Set<Integer> final_ids = intersect_all(filter_results);

        if (final_ids.isEmpty()) return Page.empty(criteria.pageable());

        return movie_repository.findMoviesByIds(new ArrayList<>(final_ids), criteria.pageable());
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