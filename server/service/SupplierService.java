package com.stockmanagement.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockmanagement.server.dto.SupplierRequest;
import com.stockmanagement.server.dto.SupplierResponse;
import com.stockmanagement.server.entity.Supplier;
import com.stockmanagement.server.repository.SupplierRepository;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    private SupplierResponse toResponse(Supplier supplier) {
        return new SupplierResponse(
            supplier.getId(),
            supplier.getName(),
            supplier.getPhone(),
            supplier.getEmail(),
            supplier.getAddress()
        );
    }

    public SupplierResponse addSupplier(SupplierRequest request) {
        validateRequest(request);

        Supplier supplier = new Supplier();
        copyRequestToSupplier(request, supplier);

        return toResponse(supplierRepository.save(supplier));
    }

    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        validateRequest(request);

        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Supplier not found"));

        copyRequestToSupplier(request, supplier);

        return toResponse(supplierRepository.save(supplier));
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplierRepository.delete(supplier);
    }

    private void validateRequest(SupplierRequest request) {
        if (request == null ||
            request.getName() == null ||
            request.getName().isBlank()) {
            throw new IllegalArgumentException("Supplier name is required.");
        }
    }

    private void copyRequestToSupplier(
            SupplierRequest request, Supplier supplier) {

        supplier.setName(request.getName().trim());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
    }
}