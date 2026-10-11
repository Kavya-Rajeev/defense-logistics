
package com.defenselogix.inventoryservice.controller;

import com.defenselogix.inventoryservice.entity.Inventory;
import com.defenselogix.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Get all inventory items
    @GetMapping
    public List<Inventory> getAllItems() {
        return inventoryService.getAllItems();
    }

    // Get one item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getItemById(@PathVariable Integer id) {
        return inventoryService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add a new inventory item
    @PostMapping
    public ResponseEntity<Inventory> addItem(@RequestBody Inventory inventory) {
        Inventory savedItem = inventoryService.addItem(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    // Update an existing inventory item
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateItem(
            @PathVariable Integer id,
            @RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.updateItem(id, inventory));
    }

    // Delete an inventory item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        inventoryService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
