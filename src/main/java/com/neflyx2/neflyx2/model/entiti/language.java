package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

@Entity
public class language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer language_id;

    @Column(length = 10)
    String language_code;
    @Column(length = 500)
    String lenguage_name;
}
