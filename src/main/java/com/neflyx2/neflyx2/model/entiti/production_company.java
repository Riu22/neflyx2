package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class production_company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer company_id;

    @Column(length = 200)
    String company_name;

    @ManyToMany(mappedBy = "production_companies")
    List<movie> movies;

}
