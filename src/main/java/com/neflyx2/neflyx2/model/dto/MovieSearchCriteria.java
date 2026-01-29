package com.neflyx2.neflyx2.model.dto;

import org.springframework.data.domain.Pageable;

public record MovieSearchCriteria(
        String title,
        Integer year,
        String genre,
        String director,
        String actor,
        String character,
        Pageable pageable
) {
    public MovieSearchCriteria{
        title = normalize(title);
        genre = normalize(genre);
        director = normalize(director);
        actor = normalize(actor);
        character = normalize(character);
    }
    private String normalize(String value){
        return value != null ? value.trim() : null;
    }

    public boolean has_title(){return title != null;}
    public boolean has_year(){return year != null;}
    public boolean has_genre(){return genre != null;}
    public boolean has_director(){return director != null;}
    public boolean has_actor(){return actor != null;}
    public boolean has_character() { return character != null; }

    public boolean has_no_filters(){
        return !has_title() && !has_year() && !has_genre() && !has_director() && !has_actor() && !has_character();
    }

}
