
package com.stockmanagement.server.dto;

import java.util.List;

public class SaleRequest {

    private List<SaleItemRequest> items;

    public SaleRequest() {
    }

    public List<SaleItemRequest> getItems() {
        return items;
    }

    public void setItems(List<SaleItemRequest> items) {
        this.items = items;
    }

    public static class SaleItemRequest {

        private Long productId;
        private Integer quantity;

        public SaleItemRequest() {
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
