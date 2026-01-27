package com.neflyx2.neflyx2.controller;

import com.neflyx2.neflyx2.model.dto.MovieListDTO;
import com.neflyx2.neflyx2.model.entiti.movie;
import com.neflyx2.neflyx2.service.genre_service;
import com.neflyx2.neflyx2.service.movie_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class movie_controller {
    @Autowired
    movie_service movie_service;

    @Autowired
    private genre_service genre_service;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("genres_list", genre_service.get_all_genre());
        return "movies";
    }

    @GetMapping("/movies/search")
    @ResponseBody
    public Page<MovieListDTO> searchMovies(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String actor,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "24") int size) {

        return movie_service.get_filtered_movies(keyword, year, genre, director, actor, page, size);
    }
}