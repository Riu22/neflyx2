package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_languages")
public class movie_languages {

    @EmbeddedId
    movie_languages_id id;

    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    private movie movie;

    @ManyToOne
    @MapsId("language_id")
    @JoinColumn(name = "language_id")
    private language language;

    @ManyToOne
    @MapsId("language_role_id")
    @JoinColumn(name = "language_role_id")
    private language_role language_role;

    public movie_languages_id getId() {
        return id;
    }

    public void setId(movie_languages_id id) {
        this.id = id;
    }

    public movie getMovie() {
        return movie;
    }

    public void setMovie(movie movie) {
        this.movie = movie;
    }

    public language getLanguage() {
        return language;
    }

    public void setLanguage(language language) {
        this.language = language;
    }

    public language_role getLanguage_role() {
        return language_role;
    }

    public void setLanguage_role(language_role language_role) {
        this.language_role = language_role;
    }
}