
package com.defenselogix.personnelservice.repository;

import com.defenselogix.personnelservice.entity.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnelRepository extends JpaRepository<Personnel, Integer> {
}
