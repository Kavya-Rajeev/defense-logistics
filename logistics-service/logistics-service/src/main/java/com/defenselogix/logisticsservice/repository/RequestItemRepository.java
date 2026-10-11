
package com.defenselogix.logisticsservice.repository;

import com.defenselogix.logisticsservice.entity.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestItemRepository
        extends JpaRepository<RequestItem, Integer> {

    List<RequestItem> findByRequestId(Integer requestId);
}
