
package com.defenselogix.assetservice.controller;

import com.defenselogix.assetservice.entity.Equipment;
import com.defenselogix.assetservice.service.EquipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets/equipment")
public class EquipmentController {

    private final EquipmentService service;

    public EquipmentController(EquipmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Equipment> getAllEquipment() {
        return service.getAllEquipment();
    }

    @GetMapping("/{id}")
    public Equipment getEquipmentById(@PathVariable Integer id) {
        return service.getEquipmentById(id);
    }

    @PostMapping
    public ResponseEntity<Equipment> addEquipment(@RequestBody Equipment equipment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addEquipment(equipment));
    }

    @PutMapping("/{id}")
    public Equipment updateEquipment(
            @PathVariable Integer id,
            @RequestBody Equipment equipment) {
        return service.updateEquipment(id, equipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Integer id) {
        service.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
