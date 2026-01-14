package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
public class department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer department_id;
    @Column(length = 200)
    String department_name;

}
