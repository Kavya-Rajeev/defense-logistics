
package com.defenselogix.logisticsservice.controller;

import com.defenselogix.logisticsservice.entity.RequestItem;
import com.defenselogix.logisticsservice.service.RequestItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics/items")
public class RequestItemController {

    private final RequestItemService service;

    public RequestItemController(RequestItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<RequestItem> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping("/{id}")
    public RequestItem getItemById(@PathVariable Integer id) {
        return service.getItemById(id);
    }

    @GetMapping("/request/{requestId}")
    public List<RequestItem> getItemsByRequestId(
            @PathVariable Integer requestId) {
        return service.getItemsByRequestId(requestId);
    }

    @PostMapping
    public ResponseEntity<RequestItem> addItem(
            @RequestBody RequestItem item) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addItem(item));
    }

    @PutMapping("/{id}")
    public RequestItem updateItem(
            @PathVariable Integer id,
            @RequestBody RequestItem item) {
        return service.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
