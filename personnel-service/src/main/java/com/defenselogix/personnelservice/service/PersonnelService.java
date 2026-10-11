
package com.defenselogix.personnelservice.service;

import com.defenselogix.personnelservice.entity.Personnel;
import com.defenselogix.personnelservice.repository.PersonnelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelService {

    private final PersonnelRepository repository;

    public PersonnelService(PersonnelRepository repository) {
        this.repository = repository;
    }

    public List<Personnel> getAllPersonnel() {
        return repository.findAll();
    }

    public Personnel getPersonnelById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personnel not found"));
    }

    public Personnel addPersonnel(Personnel personnel) {
        return repository.save(personnel);
    }

    
public Personnel updatePersonnel(Integer id, Personnel updatedPersonnel) {
    Personnel existing = getPersonnelById(id);

    existing.setFullName(updatedPersonnel.getFullName());
    existing.setRole(updatedPersonnel.getRole());
    existing.setUnitId(updatedPersonnel.getUnitId());
    existing.setPosting(updatedPersonnel.getPosting());

    if (updatedPersonnel.getAvailability() != null) {
        existing.setAvailability(updatedPersonnel.getAvailability());
    }

    return repository.save(existing);
}

    public void deletePersonnel(Integer id) {
        repository.delete(getPersonnelById(id));
    }
}

