
package com.defenselogix.assetservice.service;

import com.defenselogix.assetservice.entity.Equipment;
import com.defenselogix.assetservice.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository repository;

    public EquipmentService(EquipmentRepository repository) {
        this.repository = repository;
    }

    public List<Equipment> getAllEquipment() {
        return repository.findAll();
    }

    public Equipment getEquipmentById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    public Equipment addEquipment(Equipment equipment) {
        return repository.save(equipment);
    }

    public Equipment updateEquipment(Integer id, Equipment updatedEquipment) {
        Equipment existing = getEquipmentById(id);

        existing.setEquipmentName(updatedEquipment.getEquipmentName());
        existing.setLocation(updatedEquipment.getLocation());
        existing.setConditionStatus(updatedEquipment.getConditionStatus());
        existing.setStatus(updatedEquipment.getStatus());

        return repository.save(existing);
    }

    public void deleteEquipment(Integer id) {
        repository.delete(getEquipmentById(id));
    }
}
