package com.defenselogix.defenselogix.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    public Vehicle() {
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
}
