package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
public class language_role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer role_id;
    @Column(length = 20)
    String language_role;

}
