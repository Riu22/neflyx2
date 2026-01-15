package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_crew")
public class movie_crew {
    @EmbeddedId
    movie_crew_id id;


    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    movie movie;

    @ManyToOne
    @MapsId("person_id")
    @JoinColumn(name = "person_id")
    person person;
    @ManyToOne
    @MapsId("department_id")
    @JoinColumn(name = "department_id")
    department department;

    @Column(length = 200)
    String job;

    public movie_crew_id getId() {
        return id;
    }

    public void setId(movie_crew_id id) {
        this.id = id;
    }

    public movie getMovie() {
        return movie;
    }

    public void setMovie(movie movie) {
        this.movie = movie;
    }

    public person getPerson() {
        return person;
    }

    public void setPerson(person person) {
        this.person = person;
    }

    public department getDepartment() {
        return department;
    }

    public void setDepartment(department department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
