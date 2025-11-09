package com.irene.orderService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.irene.orderService.dto.OrderDto;
import com.irene.orderService.dto.OrderItemDto;
import com.irene.orderService.entity.Order;
import com.irene.orderService.entity.OrderItem;
import com.irene.orderService.exception.OrderNotFoundException;
import com.irene.orderService.feign.OrderInterface;
import com.irene.orderService.repository.OrderRepository;
import com.irene.productService.dto.InventoryItemRequest;
import com.irene.productService.dto.InventoryResponse;

@Service
public class OrderService{
	
	@Autowired
	OrderRepository odrRepo;
	@Autowired
	OrderInterface odrFeign;
	
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);
	
	public ResponseEntity<String> createOrder (OrderDto odrReq)
	{
		log.info("Inside createOrder service");
		boolean flag;
		Order order = new Order();
		order.setCustomerName(odrReq.getCustomerName());
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (OrderItemDto item : odrReq.getItems()) {
			OrderItem odrItem = new OrderItem();
			odrItem.setProductName(item.getProductName().toLowerCase());
			odrItem.setQuantity(item.getQuantity());
			odrItem.setOrder(order);
			orderItems.add(odrItem);
		}
		order.setItems(orderItems);
		for (OrderItem i : order.getItems()) {
			flag = reserveStock(i);
			if (!flag) {
				order.setStatus("FAILED");
				odrRepo.save(order);
				return new ResponseEntity<>("Insufficient stock for " + i.getProductName(), HttpStatus.BAD_REQUEST);
			}
		}
		order.setStatus("CONFIRMED");
		odrRepo.save(order);
		return new ResponseEntity<>("Order Confirmed", HttpStatus.CREATED);
	}

	public ResponseEntity<OrderDto> getOrderById(Long id)
	{
		log.info("Inside getOrderById Service");
		Optional<Order> byId = odrRepo.findById(id);
		if (!byId.isPresent())
			 throw new OrderNotFoundException("Order Not Found");
		
		OrderDto odrDto = new OrderDto();
		odrDto.setCustomerName(byId.get().getCustomerName());
		
		List<OrderItem> items = byId.get().getItems();
		List<OrderItemDto> odrItmDtos = new ArrayList<OrderItemDto>();
		for(OrderItem item: items)
		{
			OrderItemDto odrItmDto = new OrderItemDto(); 
			odrItmDto.setProductName(item.getProductName());
			odrItmDto.setQuantity(item.getQuantity());
			odrItmDtos.add(odrItmDto);
		}
		odrDto.setItems(odrItmDtos);
		return new ResponseEntity<>(odrDto, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<List<OrderDto>> getAllOrders() {
		log.info("Inside getAllOrders Service");
		List<Order> orders = odrRepo.findAll();
		List<OrderDto> odrDtos = new ArrayList<OrderDto>();
		
		for(Order odr: orders)
		{
			OrderDto odrDto = new OrderDto();
			odrDto.setCustomerName(odr.getCustomerName());
			List<OrderItem> items = odr.getItems();
			List<OrderItemDto> odrItmDtos = new ArrayList<OrderItemDto>();
			for(OrderItem item: items)
			{
				OrderItemDto odrItmDto = new OrderItemDto(); 
				odrItmDto.setProductName(item.getProductName());
				odrItmDto.setQuantity(item.getQuantity());
				odrItmDtos.add(odrItmDto);
			}
			odrDto.setItems(odrItmDtos);
			odrDtos.add(odrDto);
			System.out.println(odrDtos);
		}
		return new ResponseEntity<> (odrDtos, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteById(Long id){
		log.info("Inside deleteById Service");
		Optional<Order> byId = odrRepo.findById(id);
		boolean flag;
		if(!byId.isPresent())
			 throw new OrderNotFoundException("Order Not Found");
		if(byId.get().getStatus().equalsIgnoreCase("CONFIRMED") || byId.get().getStatus().equalsIgnoreCase("PAID"))
		{
			log.debug(byId.get().getCustomerName()+" "+ byId.get().getId()+ " "+ byId.get().getItems());
			for(OrderItem i :byId.get().getItems())
			{
				log.debug(i.toString());
				flag = restoreStock(i);
				if(!flag)
					log.warn("Partial stock restore for order id {}");
			    // maybe trigger alert or retry later
			}
		}
		byId.get().setStatus("CANCELLED");
		log.debug(byId.get().getCustomerName()+" "+ byId.get().getId()+ " "+ byId.get().getItems());
		odrRepo.save(byId.get());
		return new ResponseEntity<> ("Cancelled Successfully", HttpStatus.OK);
	}
	
//	reserveStock(items) → Check + reduce stock (on create)
//	restoreStock(items) → Add stock back (on cancel)
	
	public boolean reserveStock(OrderItem i)
	{
		log.info("Inside reserveStock");
		boolean res = true;
		ResponseEntity<InventoryResponse> checkStock = odrFeign.checkStock(i.getProductName());
		InventoryResponse body = checkStock.getBody();
		if(body == null || body.getAvailableQuantity() < i.getQuantity())
		{
			log.warn("Insufficient Stock");
			res = false;
			return res;
		}
		else
		{
			int avlQty = body.getAvailableQuantity();
			int updatedQty = avlQty - i.getQuantity();
			InventoryItemRequest req = new InventoryItemRequest();
			req.setAvailableQuantity(updatedQty);
			req.setProductName(i.getProductName());
			odrFeign.updateStock(req);
			log.info("Stock updated");
		}
		return res;
	}
	
	public boolean restoreStock(OrderItem item)
	{
		log.info("Inside restoreStock");
		boolean res = false;
		InventoryItemRequest req = new InventoryItemRequest();
		ResponseEntity<InventoryResponse> stockDtl = odrFeign.checkStock(item.getProductName());
		if(stockDtl.getStatusCode() == HttpStatus.NOT_FOUND)
		{
			log.warn("Product Not found");
			return res;
		}
		else
		{
			req.setAvailableQuantity(stockDtl.getBody().getAvailableQuantity()+item.getQuantity());
			req.setProductName(item.getProductName());
			odrFeign.updateStock(req);
			log.info("Stock Restored");
			res = true;
		}
		return res;
	}

}
