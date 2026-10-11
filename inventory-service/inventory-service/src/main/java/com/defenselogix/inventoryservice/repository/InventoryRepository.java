
package com.defenselogix.inventoryservice.repository;

import com.defenselogix.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
}

