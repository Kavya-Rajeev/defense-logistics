
package com.defenselogix.maintenanceservice.service;

import com.defenselogix.maintenanceservice.entity.Maintenance;
import com.defenselogix.maintenanceservice.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceService {

    private final MaintenanceRepository repository;

    public MaintenanceService(MaintenanceRepository repository) {
        this.repository = repository;
    }

    public List<Maintenance> getAllMaintenance() {
        return repository.findAll();
    }

    public Maintenance getMaintenanceById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));
    }

    public Maintenance addMaintenance(Maintenance maintenance) {
        return repository.save(maintenance);
    }

    public Maintenance updateMaintenance(Integer id, Maintenance updated) {
        Maintenance existing = getMaintenanceById(id);

        existing.setVehicleId(updated.getVehicleId());
        existing.setEquipmentId(updated.getEquipmentId());
        existing.setMaintenanceType(updated.getMaintenanceType());
        existing.setScheduledDate(updated.getScheduledDate());
        existing.setCompletedDate(updated.getCompletedDate());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());

        return repository.save(existing);
    }

    public void deleteMaintenance(Integer id) {
        repository.delete(getMaintenanceById(id));
    }
}

