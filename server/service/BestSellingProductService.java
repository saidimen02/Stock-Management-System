
package com.stockmanagement.server.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stockmanagement.server.dto.BestSellingProductResponse;
import com.stockmanagement.server.repository.SaleItemRepository;

@Service
public class BestSellingProductService {

    private final SaleItemRepository saleItemRepository;

    public BestSellingProductService(
            SaleItemRepository saleItemRepository) {
        this.saleItemRepository = saleItemRepository;
    }

    // Get best-selling products across all sales
    public List<BestSellingProductResponse> getBestSellingProducts() {
        return saleItemRepository.findBestSellingProducts();
    }

    // Get best-selling products within a date range
    public List<BestSellingProductResponse> getBestSellingProducts(
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

        return saleItemRepository
                .findBestSellingProductsBetweenDates(startDate, endDate);
    }
}
