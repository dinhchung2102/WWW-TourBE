package fit.se.tourbe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class PaymentConfig {

    // Momo configuration
    @Value("${momo.endpoint}")
    private String momoEndpoint;

    @Value("${momo.partner-code}")
    private String momoPartnerCode;

    @Value("${momo.access-key}")
    private String momoAccessKey;

    @Value("${momo.secret-key}")
    private String momoSecretKey;

    @Value("${momo.callback-url}")
    private String momoCallbackUrl;

    // VNPay configuration
    @Value("${vnpay.endpoint}")
    private String vnpayEndpoint;

    @Value("${vnpay.tmncode}")
    private String vnpayTmnCode;

    @Value("${vnpay.hashsecret}")
    private String vnpayHashSecret;

    @Value("${vnpay.callback-url}")
    private String vnpayCallbackUrl;
}

