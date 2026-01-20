package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.movie_dao;
import com.neflyx2.neflyx2.model.dto.MovieListDTO;
import com.neflyx2.neflyx2.model.entiti.movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class movie_service {
    @Autowired
    movie_dao movie_dao;

    public Page<MovieListDTO> get_filtered_movies(String title, Integer year, String genre, String director, int page, int size) {
        String titleFilter = (title != null && !title.trim().isEmpty()) ? title.trim() : null;
        String genreFilter = (genre != null && !genre.trim().isEmpty()) ? genre.trim() : null;
        String directorFilter = (director != null && !director.trim().isEmpty()) ? director.trim() : null;

        Pageable pageable = PageRequest.of(page, size);

        return movie_dao.findMoviesForList(titleFilter, year, genreFilter, directorFilter, pageable);
    }
}