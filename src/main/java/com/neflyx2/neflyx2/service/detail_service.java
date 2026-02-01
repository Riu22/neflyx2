package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.movie_repository;
import com.neflyx2.neflyx2.model.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class detail_service {
    @Autowired
    movie_repository movie_repository;


    public Optional<MovieDTO> details(int id){
        return movie_repository.findMovieById(id);
    }
}
