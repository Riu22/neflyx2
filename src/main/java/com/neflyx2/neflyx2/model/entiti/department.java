package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;
@Entity
public class department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer department_id;

    @Column(length = 200)
    String department_name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<movie_crew> crewMembers;

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
    public String getMainInfo() {
        return department_name;
    }

    public String getSecondaryInfo() {
        return (crewMembers != null) ? crewMembers.size() + " miembros" : "0 miembros";
    }

    public java.util.List<movie_crew> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(java.util.List<movie_crew> crewMembers) {
        this.crewMembers = crewMembers;
    }
}
