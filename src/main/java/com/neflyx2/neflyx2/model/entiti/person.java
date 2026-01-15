package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer person_id;
    @Column(length = 500)
    String person_name;

    @OneToMany(mappedBy = "person")
    private List<movie_cast> appearancesAsCast;

    @OneToMany(mappedBy = "person")
    private List<movie_crew> appearancesAsCrew;

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public List<movie_cast> getAppearancesAsCast() {
        return appearancesAsCast;
    }

    public void setAppearancesAsCast(List<movie_cast> appearancesAsCast) {
        this.appearancesAsCast = appearancesAsCast;
    }

    public List<movie_crew> getAppearancesAsCrew() {
        return appearancesAsCrew;
    }

    public void setAppearancesAsCrew(List<movie_crew> appearancesAsCrew) {
        this.appearancesAsCrew = appearancesAsCrew;
    }
}
