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

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public List<movie> getMovies() {
        return movies;
    }

    public void setMovies(List<movie> movies) {
        this.movies = movies;
    }
}
