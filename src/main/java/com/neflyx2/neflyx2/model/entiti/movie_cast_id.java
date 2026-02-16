package com.neflyx2.neflyx2.model.entiti;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.*;

@Embeddable
public class movie_cast_id implements Serializable {
    @Column(name = "movie_id")
    private Integer movie_id;

    @Column(name = "person_id")
    private Integer person_id;

    @Column(name = "gender_id")
    private Integer gender_id;

    @Column(name = "character_name", length = 400)
    private String character_name;

    public movie_cast_id(){}

    public movie_cast_id(Integer movie_id, Integer person_id, Integer gender_id, String character_name){
        this.movie_id = movie_id;
        this.person_id = person_id;
        this.gender_id = gender_id;
        this.character_name = character_name;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        movie_cast_id that = (movie_cast_id) o;
        return Objects.equals(movie_id, that.movie_id) &&
                Objects.equals(person_id, that.person_id) &&
                Objects.equals(gender_id, that.gender_id) &&
                Objects.equals(character_name, that.character_name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(movie_id, person_id, gender_id, character_name);
    }

    public Integer getMovie_id() { return movie_id; }
    public void setMovie_id(Integer movie_id) { this.movie_id = movie_id; }
    public Integer getPerson_id() { return person_id; }
    public void setPerson_id(Integer person_id) { this.person_id = person_id; }
    public Integer getGender_id() { return gender_id; }
    public void setGender_id(Integer gender_id) { this.gender_id = gender_id; }
    public String getCharacter_name() { return character_name; }
    public void setCharacter_name(String character_name) { this.character_name = character_name; }
}