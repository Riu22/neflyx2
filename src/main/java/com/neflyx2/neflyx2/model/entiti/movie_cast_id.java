package com.neflyx2.neflyx2.model.entiti;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Embeddable
public class movie_cast_id implements Serializable {
    @Column(name = "movie_id")
    Integer movie_id;

    @Column(name = "person_id")
    Integer person_id;
    @Column(name = "gender_id")
    Integer gender_id;

    public movie_cast_id(){}
    public movie_cast_id(Integer movie_id, Integer person_id, Integer gender_id){
        this.movie_id = movie_id;
        this.person_id = person_id;
        this.gender_id = gender_id;
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        movie_cast_id that = (movie_cast_id) o;
        return movie_id.equals(that.movie_id) && person_id.equals(that.person_id)
                && gender_id.equals(that.gender_id);
    }
    @Override
    public int hashCode(){
        return Objects.hash(movie_id, person_id, gender_id);
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public Integer getGender_id() {
        return gender_id;
    }

    public void setGender_id(Integer gender_id) {
        this.gender_id = gender_id;
    }
}
