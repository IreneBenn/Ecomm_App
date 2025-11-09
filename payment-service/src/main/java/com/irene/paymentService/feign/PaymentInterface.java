package com.irene.paymentService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PAYMENT-SERVICE")
public interface PaymentInterface 
{
	@GetMapping("/order/orders/{id}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id);
}
