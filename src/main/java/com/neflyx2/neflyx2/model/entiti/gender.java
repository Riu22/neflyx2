package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
public class gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer gender_id;
    @Column(length = 100)
    String genre_name;

}
