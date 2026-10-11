
package com.defenselogix.logisticsservice.service;

import com.defenselogix.logisticsservice.entity.RequestItem;
import com.defenselogix.logisticsservice.repository.RequestItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestItemService {

    private final RequestItemRepository repository;

    public RequestItemService(RequestItemRepository repository) {
        this.repository = repository;
    }

    public List<RequestItem> getAllItems() {
        return repository.findAll();
    }

    public List<RequestItem> getItemsByRequestId(Integer requestId) {
        return repository.findByRequestId(requestId);
    }

    public RequestItem getItemById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Request item not found"));
    }

    public RequestItem addItem(RequestItem item) {
        return repository.save(item);
    }

    public RequestItem updateItem(
            Integer id, RequestItem updatedItem) {

        RequestItem existing = getItemById(id);

        existing.setRequestId(updatedItem.getRequestId());
        existing.setResourceType(updatedItem.getResourceType());
        existing.setDescription(updatedItem.getDescription());
        existing.setQuantity(updatedItem.getQuantity());
        existing.setItemId(updatedItem.getItemId());

        return repository.save(existing);
    }

    public void deleteItem(Integer id) {
        repository.delete(getItemById(id));
    }
}
