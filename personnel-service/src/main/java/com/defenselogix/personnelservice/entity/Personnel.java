
package com.defenselogix.personnelservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "personnel")
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personnel_id")
    private Integer personnelId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "unit_id", nullable = false)
    private Integer unitId;

    @Column(name = "posting")
    private String posting;

    @Column(name = "availability", nullable = false)
    private String availability = "AVAILABLE";

    public Personnel() {
    }

    public Integer getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Integer personnelId) {
        this.personnelId = personnelId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
