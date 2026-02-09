package com.neflyx2.neflyx2.controller;

import com.neflyx2.neflyx2.model.dto.MovieDTO;
import com.neflyx2.neflyx2.model.dto.MovieListDTO;
import com.neflyx2.neflyx2.service.detail_service;
import com.neflyx2.neflyx2.service.genre_service;
import com.neflyx2.neflyx2.service.movie_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class movie_controller {

    @Autowired
    private movie_service movie_service;

    @Autowired
    private genre_service genre_service;

    @Autowired
    private detail_service detail_service;


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
            @RequestParam(required = false) String character,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "24") int size) {

        return movie_service.get_filtered_movies(
                keyword,
                year,
                genre,
                actor,
                director,
                character,
                page,
                size
        );
    }

    @GetMapping("/movies/detail/{id}")
    public String details(@PathVariable("id") int id, Model model) {
        Optional<MovieDTO> movieOpt = detail_service.details(id);

        if (movieOpt.isPresent()) {
            model.addAttribute("movie", movieOpt.get());
            return "details";
        } else {
            return "redirect:/movies?error=notfound";
        }
    }
}