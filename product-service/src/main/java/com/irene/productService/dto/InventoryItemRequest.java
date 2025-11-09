package com.irene.productService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class InventoryItemRequest {
	@NotBlank(message = "product name should not be blank")
    private String productName;
	@Min(value = 1, message = "Quantity must be at least 1")
    private int availableQuantity;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	@Override
	public String toString() {
		return "InventoryItemRequest [productName=" + productName + ", availableQuantity=" + availableQuantity + "]";
	}
}
