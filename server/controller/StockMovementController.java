package com.stockmanagement.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.stockmanagement.server.dto.StockMovementRequest;
import com.stockmanagement.server.dto.StockMovementResponse;
import com.stockmanagement.server.service.StockMovementService;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    private final StockMovementService movementService;

    public StockMovementController(StockMovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public ResponseEntity<StockMovementResponse> createMovement(
            @RequestBody StockMovementRequest request,
            Authentication authentication) {

        StockMovementResponse response = movementService.createMovement(
            authentication.getName(),
            request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<StockMovementResponse>> getAllMovements() {
        List<StockMovementResponse> movements =
            movementService.getAllMovements();

        return ResponseEntity.ok(movements);
    }
}