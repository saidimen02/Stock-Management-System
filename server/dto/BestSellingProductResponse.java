
package com.stockmanagement.server.dto;

import java.math.BigDecimal;

public class BestSellingProductResponse {

    private String productName;
    private Long totalQuantitySold;
    private BigDecimal totalRevenue;

    public BestSellingProductResponse() {
    }

    public BestSellingProductResponse(
            String productName,
            Long totalQuantitySold,
            BigDecimal totalRevenue) {
        this.productName = productName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(Long totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
