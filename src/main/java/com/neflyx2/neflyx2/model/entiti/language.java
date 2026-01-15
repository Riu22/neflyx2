package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.util.List;

@Entity
public class language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer language_id;

    @Column(length = 10)
    String language_code;
    @Column(length = 500)
    String lenguage_name;

    @OneToMany(mappedBy = "language")
    List<movie_language> movie_languages;

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

    public String getLenguage_name() {
        return lenguage_name;
    }

    public void setLenguage_name(String lenguage_name) {
        this.lenguage_name = lenguage_name;
    }

    public List<movie_language> getMovie_languages() {
        return movie_languages;
    }

    public void setMovie_languages(List<movie_language> movie_languages) {
        this.movie_languages = movie_languages;
    }
}
