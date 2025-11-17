package com.irene.orderService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.irene.orderService.dto.OrderDto;
import com.irene.orderService.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService orderservice;
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@PostMapping("/createOrder")
	public ResponseEntity<String> createOrder(@Valid @RequestBody OrderDto odrReq)
	{
		log.info("Inside createOrder");
		return orderservice.createOrder(odrReq);
	}
	
	@GetMapping("/orders/{id}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id)
	{
		log.info("Inside getOrderById");
		return orderservice.getOrderById(id);
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderDto>> getAllOrders()
	{
		log.info("Inside getAllOrders");
		return orderservice.getAllOrders();
	}
	
	@DeleteMapping("orders/{id}")
	public ResponseEntity<String> cancelOrder(@PathVariable Long id)
	{
		log.info("Inside cancelOrder");
		return orderservice.deleteById(id);
	}
}
