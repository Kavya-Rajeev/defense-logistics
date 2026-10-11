
package com.defenselogix.logisticsservice.repository;

import com.defenselogix.logisticsservice.entity.LogisticsRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticsRequestRepository
        extends JpaRepository<LogisticsRequest, Integer> {
}
