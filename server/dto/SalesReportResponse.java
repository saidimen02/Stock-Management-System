
package com.stockmanagement.server.dto;

import java.math.BigDecimal;

public class SalesReportResponse {

    private long totalSales;
    private BigDecimal totalRevenue;
    private BigDecimal averageSale;

    public SalesReportResponse() {
    }

    public SalesReportResponse(
            long totalSales,
            BigDecimal totalRevenue,
            BigDecimal averageSale) {

        this.totalSales = totalSales;
        this.totalRevenue = totalRevenue;
        this.averageSale = averageSale;
    }

    public long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(long totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getAverageSale() {
        return averageSale;
    }

    public void setAverageSale(BigDecimal averageSale) {
        this.averageSale = averageSale;
    }
}
