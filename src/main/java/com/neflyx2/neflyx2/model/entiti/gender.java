package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
public class gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer gender_id;
    @Column(length = 100)
    String genre_name;

    public Integer getGender_id() {
        return gender_id;
    }

    public void setGender_id(Integer gender_id) {
        this.gender_id = gender_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }
}
