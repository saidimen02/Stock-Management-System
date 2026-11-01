
package com.stockmanagement.server.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmanagement.server.entity.Product;
import com.stockmanagement.server.repository.ProductRepository;

@RestController
@RequestMapping("/api/low-stock")
public class LowStockController {

    private final ProductRepository productRepository;

    public LowStockController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }
}
