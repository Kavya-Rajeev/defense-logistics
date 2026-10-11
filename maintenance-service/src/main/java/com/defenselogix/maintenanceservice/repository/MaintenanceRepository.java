
package com.defenselogix.maintenanceservice.repository;

import com.defenselogix.maintenanceservice.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Integer> {
}
