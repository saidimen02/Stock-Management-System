
package com.stockmanagement.server.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.stockmanagement.server.dto.SaleRequest;
import com.stockmanagement.server.dto.SaleResponse;
import com.stockmanagement.server.dto.SaleItemResponse;
import com.stockmanagement.server.entity.Sale;
import com.stockmanagement.server.entity.SaleItem;
import com.stockmanagement.server.service.SaleService;
import com.stockmanagement.server.repository.SaleItemRepository;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;
    private final SaleItemRepository saleItemRepository;

    public SaleController(
            SaleService saleService,
            SaleItemRepository saleItemRepository) {
        this.saleService = saleService;
        this.saleItemRepository = saleItemRepository;
    }

    private SaleResponse convertToResponse(Sale sale) {

        List<SaleItemResponse> items =
                saleItemRepository.findBySaleId(sale.getId())
                .stream()
                .map(item -> new SaleItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getUnitPrice().multiply(
                                BigDecimal.valueOf(item.getQuantity())
                        )
                ))
                .toList();

        return new SaleResponse(
                sale.getId(),
                sale.getUser().getUsername(),
                sale.getUser().getRole().name(),
                sale.getTotalAmount(),
                sale.getSaleDate(),
                items
        );
    }

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(
            @RequestBody SaleRequest request,
            Authentication authentication) {

        Sale sale = saleService.createSale(
                authentication.getName(),
                request
        );

        SaleResponse response = convertToResponse(sale);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> getAllSales() {

        List<SaleResponse> responses = saleService.getAllSales()
                .stream()
                .map(this::convertToResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
