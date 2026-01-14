package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer country_id;
    @Column(length = 10)
    String country_iso_code;
    @Column(length = 200)
    String country_name;

    @ManyToMany(mappedBy = "countries")
    List<movie> movies;

}
