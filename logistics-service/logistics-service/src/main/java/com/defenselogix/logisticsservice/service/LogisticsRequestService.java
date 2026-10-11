
package com.defenselogix.logisticsservice.service;

import com.defenselogix.logisticsservice.entity.LogisticsRequest;
import com.defenselogix.logisticsservice.repository.LogisticsRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsRequestService {

    private final LogisticsRequestRepository repository;

    public LogisticsRequestService(LogisticsRequestRepository repository) {
        this.repository = repository;
    }

    public List<LogisticsRequest> getAllRequests() {
        return repository.findAll();
    }

    public LogisticsRequest getRequestById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Logistics request not found"));
    }

    public LogisticsRequest createRequest(LogisticsRequest request) {
        if (request.getStatus() == null ||
                request.getStatus().isBlank()) {
            request.setStatus("PENDING");
        }

        return repository.save(request);
    }

    public LogisticsRequest updateRequest(
            Integer id, LogisticsRequest updatedRequest) {

        LogisticsRequest existing = getRequestById(id);

        existing.setUnitId(updatedRequest.getUnitId());
        existing.setRequestedBy(updatedRequest.getRequestedBy());
        existing.setPurpose(updatedRequest.getPurpose());

        if (updatedRequest.getStatus() != null) {
            existing.setStatus(updatedRequest.getStatus());
        }

        return repository.save(existing);
    }

    public void deleteRequest(Integer id) {
        repository.delete(getRequestById(id));
    }
}
