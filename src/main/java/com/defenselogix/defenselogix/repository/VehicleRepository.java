
package com.defenselogix.defenselogix.repository;

import com.defenselogix.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Integer> {
}

