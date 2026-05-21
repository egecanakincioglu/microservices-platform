package com.flareye.paymentservice.services;

import com.flareye.paymentservice.dto.CreatePaymentRequest;
import com.flareye.paymentservice.dto.PaymentDTO;
import com.flareye.paymentservice.models.Payment;
import com.flareye.paymentservice.models.PaymentStatus;
import com.flareye.paymentservice.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment create(CreatePaymentRequest request) {
        Payment payment = new Payment();
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setPaymentMethod(request.getPaymentMethod());
        return paymentRepository.save(payment);
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> findByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }

    public List<PaymentDTO> findByUserId(String userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Payment complete(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setProcessedAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment fail(Long id, String reason) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
        payment.setStatus(PaymentStatus.FAILED);
        payment.setFailureReason(reason);
        payment.setProcessedAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment refund(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setProcessedAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .createdAt(payment.getCreatedAt())
                .processedAt(payment.getProcessedAt())
                .build();
    }
}
