package com.flareye.paymentservice.controllers;

import com.flareye.paymentservice.dto.CreatePaymentRequest;
import com.flareye.paymentservice.dto.PaymentDTO;
import com.flareye.paymentservice.models.Payment;
import com.flareye.paymentservice.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> create(@RequestBody CreatePaymentRequest request) {
        Payment payment = paymentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.toDTO(payment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findById(id);
        return payment.map(p -> ResponseEntity.ok(paymentService.toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentDTO> getByTransactionId(@PathVariable String transactionId) {
        Optional<Payment> payment = paymentService.findByTransactionId(transactionId);
        return payment.map(p -> ResponseEntity.ok(paymentService.toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDTO>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(paymentService.findByUserId(userId));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<PaymentDTO> complete(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.toDTO(paymentService.complete(id)));
    }

    @PatchMapping("/{id}/refund")
    public ResponseEntity<PaymentDTO> refund(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.toDTO(paymentService.refund(id)));
    }
}
