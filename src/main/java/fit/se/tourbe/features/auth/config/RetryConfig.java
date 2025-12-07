package fit.se.tourbe.features.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class RetryConfig {
    // Cấu hình retry mặc định được định nghĩa qua annotations
}