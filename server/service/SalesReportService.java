
package com.stockmanagement.server.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.stockmanagement.server.dto.SalesReportResponse;
import com.stockmanagement.server.repository.SaleRepository;

@Service
public class SalesReportService {

    private final SaleRepository saleRepository;

    public SalesReportService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public SalesReportResponse getSalesReport(
            LocalDateTime startDate,
            LocalDateTime endDate) {

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(
                "Start date and end date are required."
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                "Start date cannot be after end date."
            );
        }

        long totalSales = saleRepository
                .findBySaleDateBetween(startDate, endDate)
                .size();

        BigDecimal totalRevenue = saleRepository
                .getRevenueBetweenDates(startDate, endDate);

        BigDecimal averageSale = BigDecimal.ZERO;

        if (totalSales > 0) {
            averageSale = totalRevenue.divide(
                BigDecimal.valueOf(totalSales),
                2,
                RoundingMode.HALF_UP
            );
        }

        return new SalesReportResponse(
            totalSales,
            totalRevenue,
            averageSale
        );
    }
}
