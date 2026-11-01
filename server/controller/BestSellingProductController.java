
package com.stockmanagement.server.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stockmanagement.server.dto.BestSellingProductResponse;
import com.stockmanagement.server.service.BestSellingProductService;

@RestController
@RequestMapping("/api/sales/best-selling")
public class BestSellingProductController {

    private final BestSellingProductService bestSellingProductService;

    public BestSellingProductController(
            BestSellingProductService bestSellingProductService) {
        this.bestSellingProductService = bestSellingProductService;
    }

    // Get best-selling products across all sales
    @GetMapping
    public ResponseEntity<List<BestSellingProductResponse>> getBestSellingProducts() {
        return ResponseEntity.ok(
            bestSellingProductService.getBestSellingProducts()
        );
    }

    // Get best-selling products within a date range
    @GetMapping("/period")
    public ResponseEntity<List<BestSellingProductResponse>> getBestSellingProductsByPeriod(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate) {

        return ResponseEntity.ok(
            bestSellingProductService.getBestSellingProducts(
                startDate, endDate
            )
        );
    }
}
