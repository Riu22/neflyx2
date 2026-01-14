package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer genre_id;
    @Column(length = 100)
    String genre_name;

    @ManyToMany(mappedBy = "genres")
    List<movie> movies;
}
