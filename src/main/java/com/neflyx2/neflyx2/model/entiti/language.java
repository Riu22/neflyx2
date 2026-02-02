package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer language_id;

    @Column(length = 10)
    String language_code;
    @Column(length = 500)
    String language_name;

    @OneToMany(mappedBy = "language")
    List<movie_languages> movie_languages;

    public Integer getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Integer language_id) {
        this.language_id = language_id;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public List<movie_languages> getMovie_languages() {
        return movie_languages;
    }

    public void setMovie_languages(List<movie_languages> movie_languages) {
        this.movie_languages = movie_languages;
    }
}
