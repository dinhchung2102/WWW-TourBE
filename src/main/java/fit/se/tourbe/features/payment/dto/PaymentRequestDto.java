package fit.se.tourbe.features.payment.dto;

import java.math.BigDecimal;

import fit.se.tourbe.features.payment.entities.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    private String orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String returnUrl;
    private String customerEmail;
    private String description;
}