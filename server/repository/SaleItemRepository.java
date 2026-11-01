
package com.stockmanagement.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stockmanagement.server.entity.SaleItem;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    // Find all items belonging to a sale
    List<SaleItem> findBySaleId(Long saleId);

    // Get best-selling products
    @Query("""
        SELECT new com.stockmanagement.server.dto.BestSellingProductResponse(
            si.product.name,
            SUM(si.quantity),
            SUM(si.unitPrice * si.quantity)
        )
        FROM SaleItem si
        GROUP BY si.product.id, si.product.name
        ORDER BY SUM(si.quantity) DESC
        """)
    List<com.stockmanagement.server.dto.BestSellingProductResponse>
        findBestSellingProducts();

    // Get best-selling products within a date range
    @Query("""
        SELECT new com.stockmanagement.server.dto.BestSellingProductResponse(
            si.product.name,
            SUM(si.quantity),
            SUM(si.unitPrice * si.quantity)
        )
        FROM SaleItem si
        WHERE si.sale.saleDate BETWEEN :startDate AND :endDate
        GROUP BY si.product.id, si.product.name
        ORDER BY SUM(si.quantity) DESC
        """)
    List<com.stockmanagement.server.dto.BestSellingProductResponse>
        findBestSellingProductsBetweenDates(
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate
        );
}
