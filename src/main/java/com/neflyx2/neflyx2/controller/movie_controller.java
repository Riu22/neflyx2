package com.neflyx2.neflyx2.controller;

import com.neflyx2.neflyx2.model.entiti.movie;
import com.neflyx2.neflyx2.service.movie_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class movie_controller {
    @Autowired
    movie_service movie_service;


    @GetMapping("/")
    public String movies(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<movie> movies;

        if (keyword != null && !keyword.isEmpty()) {
            movies = movie_service.search_movies(keyword);
        } else {
            movies = movie_service.get_movies_titles();
        }

        model.addAttribute("movies", movies);
        model.addAttribute("keyword", keyword); // Para que el texto se mantenga en el input
        return "movies";
    }
}
