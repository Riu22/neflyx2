package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.movie_dao;
import com.neflyx2.neflyx2.model.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class detail_service {
    @Autowired
    movie_dao movie_dao;


    public Optional<MovieDTO> details(int id){
        return movie_dao.findMovieById(id);
    }
}
