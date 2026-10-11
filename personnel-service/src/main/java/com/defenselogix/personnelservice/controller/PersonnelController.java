
package com.defenselogix.personnelservice.controller;

import com.defenselogix.personnelservice.entity.Personnel;
import com.defenselogix.personnelservice.service.PersonnelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnel")
public class PersonnelController {

    private final PersonnelService service;

    public PersonnelController(PersonnelService service) {
        this.service = service;
    }

    @GetMapping
    public List<Personnel> getAllPersonnel() {
        return service.getAllPersonnel();
    }

    @GetMapping("/{id}")
    public Personnel getPersonnelById(@PathVariable Integer id) {
        return service.getPersonnelById(id);
    }

    @PostMapping
    public ResponseEntity<Personnel> addPersonnel(@RequestBody Personnel personnel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addPersonnel(personnel));
    }

    @PutMapping("/{id}")
    public Personnel updatePersonnel(
            @PathVariable Integer id,
            @RequestBody Personnel personnel) {
        return service.updatePersonnel(id, personnel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Integer id) {
        service.deletePersonnel(id);
        return ResponseEntity.noContent().build();
    }
}
