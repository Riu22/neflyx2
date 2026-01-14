package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer keyword_id;
    @Column(length = 100)
    String keyword_name;

    @ManyToMany(mappedBy = "keywords")
    List<movie> movies;
}
