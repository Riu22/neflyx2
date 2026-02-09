package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer genre_id;
    @Column(length = 100)
    String genre_name;

    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    public List<movie> getMovies() {
        return movies;
    }

    public void setMovies(List<movie> movies) {
        this.movies = movies;
    }

    @ManyToMany(mappedBy = "genres")
    List<movie> movies;

    @PreRemove
    private void remove() {
        if (movies != null) {
            for (movie m : movies) {
                m.getGenres().remove(this);
            }
        }
    }
}
