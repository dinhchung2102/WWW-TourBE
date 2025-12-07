package fit.se.tourbe.features.payment.dto;

import java.math.BigDecimal;

import fit.se.tourbe.features.payment.entities.PaymentMethod;
import fit.se.tourbe.features.payment.entities.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private String orderId;
    private String transactionId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String paymentUrl;
    private String responseCode;
    private String responseMessage;
}