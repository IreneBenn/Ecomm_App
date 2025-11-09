package com.irene.paymentService.exception;

public class ProductNotFoundException extends RuntimeException{
	
	ProductNotFoundException(String msg)
	{
		super(msg);
	}

}
