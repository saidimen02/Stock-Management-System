
package com.stockmanagement.server.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.stockmanagement.server.dto.DashboardResponse;
import com.stockmanagement.server.repository.ProductRepository;
import com.stockmanagement.server.repository.CategoryRepository;
import com.stockmanagement.server.repository.SupplierRepository;
import com.stockmanagement.server.repository.SaleRepository;

@Service
public class DashboardService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final SaleRepository saleRepository;

    public DashboardService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            SupplierRepository supplierRepository,
            SaleRepository saleRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.saleRepository = saleRepository;
    }

    public DashboardResponse getDashboardStatistics() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalProducts(productRepository.count());

        response.setTotalCategories(categoryRepository.count());

        response.setTotalSuppliers(supplierRepository.count());

        response.setTotalSales(saleRepository.count());

        BigDecimal revenue = saleRepository.getTotalRevenue();

        response.setTotalRevenue(
                revenue != null ? revenue : BigDecimal.ZERO
        );

        response.setLowStockProducts(
                productRepository.findLowStockProducts().size()
        );

        return response;
    }
}
