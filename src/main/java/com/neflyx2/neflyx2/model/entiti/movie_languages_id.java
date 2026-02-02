package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class movie_languages_id implements Serializable {
    Integer movie_id;
    Integer language_id;
    Integer language_role_id;

    public movie_languages_id() {}

    public movie_languages_id(Integer movie_id, Integer language_id, Integer language_role_id) {
        this.movie_id = movie_id;
        this.language_id = language_id;
        this.language_role_id = language_role_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof movie_languages_id that)) return false;
        return Objects.equals(movie_id, that.movie_id) &&
                Objects.equals(language_id, that.language_id) &&
                Objects.equals(language_role_id, that.language_role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie_id, language_id, language_role_id);
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Integer getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Integer language_id) {
        this.language_id = language_id;
    }

    public Integer getLanguage_role_id() {
        return language_role_id;
    }

    public void setLanguage_role_id(Integer language_role_id) {
        this.language_role_id = language_role_id;
    }
}
