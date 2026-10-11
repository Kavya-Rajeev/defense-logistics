
package com.defenselogix.maintenanceservice.controller;

import com.defenselogix.maintenanceservice.entity.Maintenance;
import com.defenselogix.maintenanceservice.service.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService service;

    public MaintenanceController(MaintenanceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Maintenance> getAllMaintenance() {
        return service.getAllMaintenance();
    }

    @GetMapping("/{id}")
    public Maintenance getMaintenanceById(@PathVariable Integer id) {
        return service.getMaintenanceById(id);
    }

    @PostMapping
    public ResponseEntity<Maintenance> addMaintenance(
            @RequestBody Maintenance maintenance) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addMaintenance(maintenance));
    }

    @PutMapping("/{id}")
    public Maintenance updateMaintenance(
            @PathVariable Integer id,
            @RequestBody Maintenance maintenance) {
        return service.updateMaintenance(id, maintenance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Integer id) {
        service.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
