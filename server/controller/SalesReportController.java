
package com.stockmanagement.server.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stockmanagement.server.dto.SalesReportResponse;
import com.stockmanagement.server.service.SalesReportService;

@RestController
@RequestMapping("/api/sales/reports")
public class SalesReportController {

    private final SalesReportService salesReportService;

    public SalesReportController(
            SalesReportService salesReportService) {
        this.salesReportService = salesReportService;
    }

    @GetMapping
    public ResponseEntity<SalesReportResponse> getSalesReport(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate) {

        SalesReportResponse report =
                salesReportService.getSalesReport(startDate, endDate);

        return ResponseEntity.ok(report);
    }
}
