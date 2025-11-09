package com.irene.productService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irene.productService.entity.Inventory;

@Repository
public interface ProductRepository extends JpaRepository<Inventory, Long> {

	Inventory findByProductName(String prdtName);

}
