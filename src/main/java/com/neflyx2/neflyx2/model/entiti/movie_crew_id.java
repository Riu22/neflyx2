package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class movie_crew_id implements Serializable {
    @Column(name = "movie_id")
    Integer movie_id;
    @Column(name = "person_id")
    Integer person_id;
    @Column(name = "department_id")
    Integer department_id;


    public movie_crew_id(){}
    public movie_crew_id(Integer movie_id, Integer person_id, Integer department_id){
        this.movie_id = movie_id;
        this.person_id = person_id;
        this.department_id = department_id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        movie_crew_id that = (movie_crew_id) o;
        return movie_id.equals(that.movie_id) && person_id.equals(that.person_id)
                && department_id.equals(that.department_id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(movie_id, person_id, department_id);
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

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }


}
