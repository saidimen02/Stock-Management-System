
package com.stockmanagement.server.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SaleResponse {

    private Long id;
    private String username;
    private String role;
    private BigDecimal totalAmount;
    private LocalDateTime saleDate;
    private List<SaleItemResponse> items;

    public SaleResponse() {
    }

    public SaleResponse(
            Long id,
            String username,
            String role,
            BigDecimal totalAmount,
            LocalDateTime saleDate,
            List<SaleItemResponse> items) {

        this.id = id;
        this.username = username;
        this.role = role;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public List<SaleItemResponse> getItems() {
        return items;
    }

    public void setItems(List<SaleItemResponse> items) {
        this.items = items;
    }
}
