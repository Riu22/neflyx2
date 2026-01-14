package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class movie {
    // utilizar anotacion embeded id para las compuestas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer movie_id;
    @Column(length = 1000)
    String title;
    int budget;
    @Column(length = 1000)
    String homepage;
    @Column(length = 1000)
    String overview;
    @Column(precision = 12,scale = 6)
    BigDecimal popularity;
    @Column(name = "release_date")
    LocalDate release_date;

    Long revenue;

    int runtime;
    @Column(length = 50)
    String movie_status;
    @Column(length = 1000)
    String tagline;
    @Column(precision = 4,scale = 2)
    BigDecimal vote_average;

    int vote_count;

    @ManyToMany
    @JoinTable(
            name ="production_country",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    List<country> countries;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    List<genre> genres;

    @ManyToMany
    @JoinTable(
            name = "movie_keywords",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    List<keyword> keywords;

    @ManyToMany
    @JoinTable(
            name = "movie_company",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    List<production_company> production_companies;
}
