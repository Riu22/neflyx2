package com.neflyx2.neflyx2.model.dto;

import org.springframework.data.domain.Pageable;

public record MovieSearchCriteria(
        String title,
        Integer year,
        String genre,
        String director,
        Pageable pageable
) {
    public MovieSearchCriteria{
        title = normalize(title);
        genre = normalize(genre);
        director = normalize(director);
    }
    private String normalize(String value){
        return value != null ? value.trim() : null;
    }

    public boolean has_title(){return title != null;}
    public boolean has_year(){return year != null;}
    public boolean has_genre(){return genre != null;}
    public boolean has_director(){return director != null;}

    public boolean has_no_filters(){
        return !has_title() && !has_year() && !has_genre() && !has_director();
    }
    public int get_active_filters_count(){
        return (has_title() ? 1 : 0) + (has_year() ? 1 : 0) + (has_genre() ? 1 : 0) + (has_director() ? 1 : 0);
    }
}
