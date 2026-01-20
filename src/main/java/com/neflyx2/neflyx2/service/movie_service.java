package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.movie_dao;
import com.neflyx2.neflyx2.model.entiti.movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class movie_service {
    @Autowired
    movie_dao movie_dao;


    public List<movie> get_movies_titles(){
        return movie_dao.findAll();
    }

    public List<movie> search_movies(String keyword) {
        return movie_dao.findByTitleContainingIgnoreCase(keyword);
    }
}
