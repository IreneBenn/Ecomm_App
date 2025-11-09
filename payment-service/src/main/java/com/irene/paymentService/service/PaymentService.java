package com.irene.paymentService.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.irene.orderService.exception.OrderNotFoundException;
import com.irene.paymentService.dto.PaymentRequestDto;
import com.irene.paymentService.dto.PaymentResponseDto;
import com.irene.paymentService.entity.Payment;
import com.irene.paymentService.entity.util.PaymentStatus;
import com.irene.paymentService.feign.PaymentInterface;
import com.irene.paymentService.repository.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	PaymentInterface pymtInt;
	
	@Autowired
	PaymentRepository repo;
	public ResponseEntity<String> makePayment(PaymentRequestDto req) {
		Payment pymt = new Payment();
		pymt.setOrderId(req.getOrderId());
		pymt.setAmount(req.getAmount());
		try 
		{
			ResponseEntity<OrderDto> orderById = pymtInt.getOrderById(req.getOrderId());
			if(orderById.getStatusCode() == HttpStatus.ACCEPTED)
			{
				pymt.setPaymentMethod(req.getPaymentMethod());
				PaymentStatus status = simulatePayment(req.getPaymentMethod(), req.getAmount());
				if(status.equals(PaymentStatus.SUCCESS))
				{
					pymt.setStatus(PaymentStatus.SUCCESS);
				}
				else
				{
					pymt.setStatus(PaymentStatus.FAILED);
					repo.save(pymt);
					return new ResponseEntity<>("Payment Failed!! Invalid Payment Method", HttpStatus.BAD_REQUEST);	
				}
				repo.save(pymt);
			}
		}
		catch(OrderNotFoundException ex)
		{
			pymt.setPaymentMethod(req.getPaymentMethod());
			pymt.setStatus(PaymentStatus.FAILED);
			repo.save(pymt);
			return new ResponseEntity<>("Payment Failed!! No Such Order found", HttpStatus.BAD_REQUEST);	
		}
		return new ResponseEntity<>("Payment Success", HttpStatus.OK);		
	}

	private PaymentStatus simulatePayment(String paymentMethod, double amt) {
		
		Random random = new Random();
		switch (paymentMethod) 
		{
			case "CARD":
				return amt > 10000 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
	
			case "UPI":
				return random.nextDouble() < 0.7 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
	
			case "NET_BANKING":
				return amt % 100 == 0 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
			default:
				return PaymentStatus.FAILED;
		}
	}
}
