package com.irene.paymentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irene.paymentService.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
