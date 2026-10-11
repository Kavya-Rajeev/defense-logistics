
package com.defenselogix.logisticsservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "request_items")
public class RequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_item_id")
    private Integer requestItemId;

    @Column(name = "request_id", nullable = false)
    private Integer requestId;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    @Column(name = "item_id")
    private Integer itemId;

    public RequestItem() {
    }

    public Integer getRequestItemId() {
        return requestItemId;
    }

    public void setRequestItemId(Integer requestItemId) {
        this.requestItemId = requestItemId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
