
package com.stockmanagement.server.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockmanagement.server.dto.StockMovementRequest;
import com.stockmanagement.server.dto.StockMovementResponse;
import com.stockmanagement.server.entity.Product;
import com.stockmanagement.server.entity.StockMovement;
import com.stockmanagement.server.entity.Supplier;
import com.stockmanagement.server.entity.User;
import com.stockmanagement.server.repository.ProductRepository;
import com.stockmanagement.server.repository.StockMovementRepository;
import com.stockmanagement.server.repository.SupplierRepository;
import com.stockmanagement.server.repository.UserRepository;

@Service
public class StockMovementService {

    private final StockMovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;

    public StockMovementService(
            StockMovementRepository movementRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            SupplierRepository supplierRepository) {

        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public StockMovementResponse createMovement(
            String username,
            StockMovementRequest request) {

        if (request == null ||
                request.getProductId() == null ||
                request.getQuantityChange() == null ||
                request.getQuantityChange() == 0 ||
                request.getReason() == null ||
                request.getReason().isBlank()) {

            throw new IllegalArgumentException("Invalid stock movement.");
        }

        // Find product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find supplier (optional)
        Supplier supplier = null;

        if (request.getSupplierId() != null) {
            supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
        }

        // Calculate new stock quantity
        int newQuantity = product.getQuantity() + request.getQuantityChange();

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient stock.");
        }

        // Update product stock
        product.setQuantity(newQuantity);
        productRepository.save(product);

        // Create stock movement
        StockMovement movement = new StockMovement();

        movement.setProduct(product);
        movement.setUser(user);
        movement.setSupplier(supplier);
        movement.setQuantityChange(request.getQuantityChange());
        movement.setReason(request.getReason());
        movement.setMovementDate(LocalDateTime.now());

        StockMovement saved = movementRepository.save(movement);

        // Return response with supplier information
        return buildResponse(saved);
    }

    // Get all stock movements
    public List<StockMovementResponse> getAllMovements() {

        return movementRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    // Convert StockMovement entity to response DTO
    private StockMovementResponse buildResponse(StockMovement movement) {

        StockMovementResponse response = new StockMovementResponse(
                movement.getId(),
                movement.getProduct().getId(),
                movement.getProduct().getName(),
                movement.getUser().getUsername(),
                movement.getQuantityChange(),
                movement.getReason(),
                movement.getMovementDate()
        );

        // Add supplier information if available
        if (movement.getSupplier() != null) {
            response.setSupplierId(movement.getSupplier().getId());
            response.setSupplierName(movement.getSupplier().getName());
        }

        return response;
    }
}
