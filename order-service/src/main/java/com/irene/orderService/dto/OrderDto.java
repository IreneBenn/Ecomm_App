package com.irene.orderService.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class OrderDto {
	@NotBlank(message = "Customer name is required")
	private String customerName;
	@Valid //to cascade to OrderItemDto
	@NotEmpty(message = "Order items cannot be empty")
	private List<OrderItemDto> items;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<OrderItemDto> getItems() {
		return items;
	}
	public void setItems(List<OrderItemDto> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "OrderRequest [customerName=" + customerName + ", items=" + items + "]";
	}
}
