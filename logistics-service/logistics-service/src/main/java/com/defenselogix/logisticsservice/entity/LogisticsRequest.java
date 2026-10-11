
package com.defenselogix.logisticsservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logistics_requests")
public class LogisticsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "unit_id", nullable = false)
    private Integer unitId;

    @Column(name = "requested_by", nullable = false)
    private Integer requestedBy;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Column(name = "request_date", insertable = false, updatable = false)
    private LocalDateTime requestDate;

    @Column(name = "status")
    private String status;

    public LogisticsRequest() {
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Integer requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
