package com.stockmanagement.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stockmanagement.server.entity.StockMovement;

public interface StockMovementRepository
        extends JpaRepository<StockMovement, Long> {
}