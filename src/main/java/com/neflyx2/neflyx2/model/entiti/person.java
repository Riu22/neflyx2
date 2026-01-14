package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
public class person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer person_id;
    @Column(length = 500)
    String person_name;

}
