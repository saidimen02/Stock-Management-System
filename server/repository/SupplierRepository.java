package com.stockmanagement.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stockmanagement.server.entity.Supplier;

public interface SupplierRepository
        extends JpaRepository<Supplier, Long> {
}