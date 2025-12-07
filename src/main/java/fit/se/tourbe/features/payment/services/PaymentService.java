package fit.se.tourbe.features.payment.services;

import fit.se.tourbe.features.payment.dto.PaymentRequestDto;
import fit.se.tourbe.features.payment.dto.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto request);

    PaymentResponseDto getPaymentByOrderId(String orderId);
}