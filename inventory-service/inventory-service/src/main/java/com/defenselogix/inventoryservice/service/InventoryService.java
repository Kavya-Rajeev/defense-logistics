
package com.defenselogix.inventoryservice.service;

import com.defenselogix.inventoryservice.entity.Inventory;
import com.defenselogix.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getItemById(Integer id) {
        return inventoryRepository.findById(id);
    }

    public Inventory addItem(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory updateItem(Integer id, Inventory updatedItem) {
        Inventory existingItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));

        existingItem.setItemName(updatedItem.getItemName());
        existingItem.setQuantity(updatedItem.getQuantity());
        existingItem.setMinimumStock(updatedItem.getMinimumStock());

        return inventoryRepository.save(existingItem);
    }

    public void deleteItem(Integer id) {
        inventoryRepository.deleteById(id);
    }
}

