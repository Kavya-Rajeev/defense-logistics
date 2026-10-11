
package com.defenselogix.transportservice.repository;

import com.defenselogix.transportservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
