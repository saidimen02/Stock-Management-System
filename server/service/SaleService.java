
package com.stockmanagement.server.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockmanagement.server.dto.SaleRequest;
import com.stockmanagement.server.entity.Sale;
import com.stockmanagement.server.entity.SaleItem;
import com.stockmanagement.server.entity.Product;
import com.stockmanagement.server.entity.User;

import com.stockmanagement.server.repository.SaleRepository;
import com.stockmanagement.server.repository.SaleItemRepository;
import com.stockmanagement.server.repository.ProductRepository;
import com.stockmanagement.server.repository.UserRepository;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SaleService(
            SaleRepository saleRepository,
            SaleItemRepository saleItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Sale createSale(String username, SaleRequest request) {
    	
        if (request == null ||
         
            request.getItems() == null ||
            request.getItems().isEmpty()) {

            throw new IllegalArgumentException("Invalid sale request.");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                    new RuntimeException("User not found")
                );

        Sale sale = new Sale();
        sale.setUser(user);
        sale.setSaleDate(LocalDateTime.now());
        sale.setTotalAmount(BigDecimal.ZERO);

        sale = saleRepository.save(sale);

        BigDecimal total = BigDecimal.ZERO;

        for (SaleRequest.SaleItemRequest requestedItem : request.getItems()) {

            if (requestedItem == null ||
                requestedItem.getProductId() == null ||
                requestedItem.getQuantity() == null ||
                requestedItem.getQuantity() <= 0) {

                throw new IllegalArgumentException("Invalid sale item.");
            }

            Product product = productRepository.findById(
                    requestedItem.getProductId())
                    .orElseThrow(() ->
                        new RuntimeException("Product not found")
                    );

            if (product.getQuantity() == null ||
                product.getQuantity() < requestedItem.getQuantity()) {

                throw new IllegalArgumentException(
                    "Insufficient stock for " + product.getName()
                );
            }

            BigDecimal unitPrice = product.getPrice();

            if (unitPrice == null ||
                unitPrice.compareTo(BigDecimal.ZERO) < 0) {

                throw new IllegalArgumentException("Invalid product price.");
            }

            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setProduct(product);
            item.setQuantity(requestedItem.getQuantity());
            item.setUnitPrice(unitPrice);

            BigDecimal subtotal = unitPrice.multiply(
                BigDecimal.valueOf(requestedItem.getQuantity())
            );

            total = total.add(subtotal);

            product.setQuantity(
                product.getQuantity() - requestedItem.getQuantity()
            );

            productRepository.save(product);
            saleItemRepository.save(item);
        }

        sale.setTotalAmount(total);

        return saleRepository.save(sale);
    }
    
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
}
