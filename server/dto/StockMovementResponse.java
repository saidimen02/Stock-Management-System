package com.stockmanagement.server.dto;

import java.time.LocalDateTime;

public class StockMovementResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String username;
    private Integer quantityChange;
    private String reason;
    private LocalDateTime movementDate;
    private Long supplierId;
    private String supplierName;

    public StockMovementResponse() {
    }

    public StockMovementResponse(
            Long id,
            Long productId,
            String productName,
            String username,
            Integer quantityChange,
            String reason,
            LocalDateTime movementDate) {

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.username = username;
        this.quantityChange = quantityChange;
        this.reason = reason;
        this.movementDate = movementDate;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getUsername() {
        return username;
    }

    public Integer getQuantityChange() {
        return quantityChange;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}