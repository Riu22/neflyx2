package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_cast")
public class movie_cast {
    @EmbeddedId
    movie_cast_id id;

    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    movie movie;

    @ManyToOne
    @MapsId("person_id")
    @JoinColumn(name = "person_id")
    person person;
    @ManyToOne
    @MapsId("gender_id")
    @JoinColumn(name = "gender_id")
    gender gender;

    @Column(length = 400)
    String character_name;

    Integer cast_order;

    public movie_cast_id getId() {
        return id;
    }

    public void setId(movie_cast_id id) {
        this.id = id;
    }

    public movie getMovie() {
        return movie;
    }

    public void setMovie(movie movie) {
        this.movie = movie;
    }

    public person getPerson() {
        return person;
    }

    public void setPerson(person person) {
        this.person = person;
    }

    public gender getGender() {
        return gender;
    }

    public void setGender(gender gender) {
        this.gender = gender;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public Integer getCast_order() {
        return cast_order;
    }

    public void setCast_order(Integer cast_order) {
        this.cast_order = cast_order;
    }
}
