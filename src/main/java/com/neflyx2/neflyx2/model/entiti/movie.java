package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer movie_id;
    @Column(length = 1000)
    String title;
    Integer budget;
    @Column(length = 1000)
    String homepage;
    @Column(length = 1000)
    String overview;
    @Column(precision = 12,scale = 6)
    BigDecimal popularity;
    @Column(name = "release_date")
    LocalDate release_date;

    Long revenue;

    Integer runtime;
    @Column(length = 50)
    String movie_status;
    @Column(length = 1000)
    String tagline;
    @Column(precision = 4,scale = 2)
    BigDecimal vote_average;

    Integer vote_count;

    @ManyToMany
    @JoinTable(
            name ="production_country",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    Set<country> countries;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    Set<genre> genres;

    @ManyToMany
    @JoinTable(
            name = "movie_keywords",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    Set<keyword> keywords;

    @ManyToMany
    @JoinTable(
            name = "movie_company",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    Set<production_company> production_companies;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    Set<movie_cast> cast;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    Set<movie_crew> crew;

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public BigDecimal getPopularity() {
        return popularity;
    }

    public void setPopularity(BigDecimal popularity) {
        this.popularity = popularity;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getMovie_status() {
        return movie_status;
    }

    public void setMovie_status(String movie_status) {
        this.movie_status = movie_status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public BigDecimal getVote_average() {
        return vote_average;
    }

    public void setVote_average(BigDecimal vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Set<country> getCountries() {
        return countries;
    }

    public void setCountries(Set<country> countries) {
        this.countries = countries;
    }

    public Set<genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<genre> genres) {
        this.genres = genres;
    }

    public Set<keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<keyword> keywords) {
        this.keywords = keywords;
    }

    public Set<production_company> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(Set<production_company> production_companies) {
        this.production_companies = production_companies;
    }

    public Set<movie_cast> getCast() {
        return cast;
    }

    public void setCast(Set<movie_cast> cast) {
        this.cast = cast;
    }

    public Set<movie_crew> getCrew() {
        return crew;
    }

    public void setCrew(Set<movie_crew> crew) {
        this.crew = crew;
    }
}
