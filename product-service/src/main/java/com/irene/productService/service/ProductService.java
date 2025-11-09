package com.irene.productService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.irene.productService.dto.InventoryItemRequest;
import com.irene.productService.dto.InventoryResponse;
import com.irene.productService.entity.Inventory;
import com.irene.productService.exception.ProductNotFoundException;
import com.irene.productService.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository prdRepo;
	
	public ResponseEntity<String> addProduct(InventoryItemRequest req) {
		Inventory inventory = new Inventory();
		inventory.setProductName(req.getProductName().toLowerCase());
		inventory.setAvailableQuantity(req.getAvailableQuantity());
		Inventory save = prdRepo.save(inventory);
		System.out.println(save);
		return new ResponseEntity<> ("Added Successfully!", HttpStatus.CREATED);
	}

	public ResponseEntity<List<InventoryResponse>> getAllProducts() {
		List<Inventory> allProducts = prdRepo.findAll();
		List<InventoryResponse> resp = new ArrayList<InventoryResponse>();
		for(Inventory i:allProducts)
		{
			InventoryResponse invtResp = new InventoryResponse(i.getProductName().toUpperCase(), i.getAvailableQuantity());
			resp.add(invtResp);
		}
		System.out.println(resp);
		return new ResponseEntity<> (resp, HttpStatus.OK);
	}

	public ResponseEntity<InventoryResponse> getStockDtlsForPrdt(String prdtName) {
		System.out.println("inside getStockDtlsForPrdt service");
		System.out.println("before");
		Inventory byProductName = prdRepo.findByProductName(prdtName.toLowerCase());
		System.out.println("after");
		if(byProductName == null)
			throw new ProductNotFoundException("Product Not found");
		InventoryResponse resp = new InventoryResponse(byProductName.getProductName(), byProductName.getAvailableQuantity());
		return new ResponseEntity<> (resp, HttpStatus.OK);
	}

	public ResponseEntity<String> updateProduct(InventoryItemRequest req) {
			System.out.println("inside updateProduct service");
			Inventory product = prdRepo.findByProductName(req.getProductName().toLowerCase());
			System.out.println("before");
			product.setAvailableQuantity(req.getAvailableQuantity());
			prdRepo.save(product);
			System.out.println("after");
		return ResponseEntity.ok().body("Updated Successfully!");
	}

}
