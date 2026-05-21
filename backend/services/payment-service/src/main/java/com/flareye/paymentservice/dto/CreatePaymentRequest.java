package com.flareye.paymentservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {
    private String userId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
}
