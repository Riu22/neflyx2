package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class language_role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer role_id;
    @Column(length = 20)
    String language_role;

    @OneToMany(mappedBy = "language_role")
    List<movie_language> movie_languages_rol;

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getLanguage_role() {
        return language_role;
    }

    public void setLanguage_role(String language_role) {
        this.language_role = language_role;
    }

    public List<movie_language> getMovie_languages_rol() {
        return movie_languages_rol;
    }

    public void setMovie_languages_rol(List<movie_language> movie_languages_rol) {
        this.movie_languages_rol = movie_languages_rol;
    }
}
