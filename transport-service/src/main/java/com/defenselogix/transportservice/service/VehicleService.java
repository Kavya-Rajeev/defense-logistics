
package com.defenselogix.transportservice.service;

import com.defenselogix.transportservice.entity.Vehicle;
import com.defenselogix.transportservice.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public Vehicle getVehicleById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public Vehicle updateVehicle(Integer id, Vehicle updatedVehicle) {
        Vehicle existing = getVehicleById(id);

        existing.setVehicleType(updatedVehicle.getVehicleType());
        existing.setLocation(updatedVehicle.getLocation());
        existing.setStatus(updatedVehicle.getStatus());

        return repository.save(existing);
    }

    public void deleteVehicle(Integer id) {
        repository.delete(getVehicleById(id));
    }
}

