package com.irene.productService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.irene.productService.dto.InventoryItemRequest;
import com.irene.productService.dto.InventoryResponse;
import com.irene.productService.service.ProductService;

@RestController
@RequestMapping("/Inventory")
public class ProductController {

	@Autowired
	ProductService productservice;

	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@RequestBody InventoryItemRequest req) {
		return productservice.addProduct(req);
	}

	@GetMapping("/{productName}")
	public ResponseEntity<InventoryResponse> checkStock(@PathVariable("productName") String prdtName) {
		System.out.println("Inside checkStock Cntlr");
		return productservice.getStockDtlsForPrdt(prdtName);
	}

	@GetMapping("/")
	public ResponseEntity<List<InventoryResponse>> listAllProducts() {
		return productservice.getAllProducts();
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateStock(@RequestBody InventoryItemRequest req) {
		System.out.println("Inside updateStock Cntlr");
		return productservice.updateProduct(req);
	}
}
