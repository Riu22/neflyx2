package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.genre_dao;
import com.neflyx2.neflyx2.model.entiti.genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class genre_service {
    @Autowired
    genre_dao genre_dao;

    public List<genre> get_all_genre(){
        return genre_dao.findAll();
    }
}
