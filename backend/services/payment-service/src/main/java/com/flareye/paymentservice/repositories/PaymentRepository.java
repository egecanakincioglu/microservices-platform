package com.flareye.paymentservice.repositories;

import com.flareye.paymentservice.models.Payment;
import com.flareye.paymentservice.models.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByUserId(String userId);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByUserIdAndStatus(String userId, PaymentStatus status);
}
