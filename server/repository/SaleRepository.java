
package com.stockmanagement.server.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stockmanagement.server.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Count all sales
    long count();

    // Calculate total revenue
    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s")
    BigDecimal getTotalRevenue();

    // Find sales between two dates
    List<Sale> findBySaleDateBetween(
        LocalDateTime startDate,
        LocalDateTime endDate
    );

    // Calculate revenue between two dates
    @Query("""
        SELECT COALESCE(SUM(s.totalAmount), 0)
        FROM Sale s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
        """)
    BigDecimal getRevenueBetweenDates(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}
