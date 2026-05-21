package com.flareye.paymentservice.dto;

import com.flareye.paymentservice.models.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private String transactionId;
    private String userId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}
