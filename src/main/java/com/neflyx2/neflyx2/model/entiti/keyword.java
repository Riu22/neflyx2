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

    public Integer getKeyword_id() {
        return keyword_id;
    }

    public void setKeyword_id(Integer keyword_id) {
        this.keyword_id = keyword_id;
    }

    public String getKeyword_name() {
        return keyword_name;
    }

    public void setKeyword_name(String keyword_name) {
        this.keyword_name = keyword_name;
    }

    public List<movie> getMovies() {
        return movies;
    }

    public void setMovies(List<movie> movies) {
        this.movies = movies;
    }

    public String getMainInfo() {
        return keyword_name;
    }

    public String getSecondaryInfo() {
        return (movies != null) ? movies.size() + " películas" : "0 películas";
    }

    @PreRemove
    private void removeKeywordsFromMovies() {
        if (movies != null) {
            for (movie m : movies) {
                m.getKeywords().remove(this);
            }
        }
    }
}
