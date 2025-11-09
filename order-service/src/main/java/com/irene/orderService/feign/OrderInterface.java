package com.irene.orderService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.irene.productService.dto.InventoryItemRequest;
import com.irene.productService.dto.InventoryResponse;

@FeignClient("PRODUCT-SERVICE")
public interface OrderInterface {
	@GetMapping("/Inventory/{productName}")
	public ResponseEntity<InventoryResponse> checkStock(@PathVariable("productName") String prdtName);
	
	@PutMapping("/Inventory/update")
	public ResponseEntity<String> updateStock(@RequestBody InventoryItemRequest req) ;
}
