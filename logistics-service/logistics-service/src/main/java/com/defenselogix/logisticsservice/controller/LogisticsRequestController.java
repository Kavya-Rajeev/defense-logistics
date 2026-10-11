
package com.defenselogix.logisticsservice.controller;

import com.defenselogix.logisticsservice.entity.LogisticsRequest;
import com.defenselogix.logisticsservice.service.LogisticsRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics/requests")
public class LogisticsRequestController {

    private final LogisticsRequestService service;

    public LogisticsRequestController(LogisticsRequestService service) {
        this.service = service;
    }

    @GetMapping
    public List<LogisticsRequest> getAllRequests() {
        return service.getAllRequests();
    }

    @GetMapping("/{id}")
    public LogisticsRequest getRequestById(@PathVariable Integer id) {
        return service.getRequestById(id);
    }

    @PostMapping
    public ResponseEntity<LogisticsRequest> createRequest(
            @RequestBody LogisticsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createRequest(request));
    }

    @PutMapping("/{id}")
    public LogisticsRequest updateRequest(
            @PathVariable Integer id,
            @RequestBody LogisticsRequest request) {
        return service.updateRequest(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Integer id) {
        service.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}
