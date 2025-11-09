package com.irene.orderService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class OrderItemDto {
	@NotBlank(message = "Product name cannot be empty")
    private String productName;
	@Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "OrderItemDto [productName=" + productName + ", quantity=" + quantity + "]";
	}
}