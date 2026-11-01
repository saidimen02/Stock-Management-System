
package com.stockmanagement.server.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank(message = "Product name is required.")
    private String name;

    private String description;

    @NotNull(message = "Product price is required.")
    @DecimalMin(value = "0.01", message = "Product price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "Product quantity is required.")
    @Min(value = 0, message = "Product quantity cannot be negative.")
    private Integer quantity;

    @NotNull(message = "Minimum stock is required.")
    @Min(value = 0, message = "Minimum stock cannot be negative.")
    private Integer minStock = 5;

    @NotNull(message = "Category is required.")
    @Min(value = 1, message = "Category ID must be positive.")
    private Long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
