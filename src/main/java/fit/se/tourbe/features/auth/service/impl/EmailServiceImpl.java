package fit.se.tourbe.features.auth.service.impl;

import fit.se.tourbe.features.auth.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Override
    public void sendOtpEmail(String to, String otp) {
        try {
            // Check if email is configured
            if (fromEmail == null || fromEmail.isEmpty() || fromEmail.equals("your-email@gmail.com")) {
                logger.warn("Email service not configured. OTP for {}: {}", to, otp);
                logger.warn("Please configure email in application.properties to send emails");
                // In development, just log the OTP - don't throw error
                return;
            }
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Xác thực email đăng ký - Tour Booking");
            message.setText("Xin chào,\n\n" +
                    "Mã OTP của bạn là: " + otp + "\n\n" +
                    "Mã này có hiệu lực trong 5 phút.\n\n" +
                    "Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.\n\n" +
                    "Trân trọng,\n" +
                    "Tour Booking Team");
            
            mailSender.send(message);
            logger.info("OTP email sent successfully to: {}", to);
        } catch (org.springframework.mail.MailAuthenticationException e) {
            logger.error("Email authentication failed. OTP for {}: {}", to, otp);
            logger.error("Please check email configuration in application.properties");
            logger.error("Error details: {}", e.getMessage());
            // Log OTP for development/testing purposes
            logger.warn("=== OTP FOR TESTING === Email: {}, OTP: {} ===", to, otp);
            // Don't throw exception in development - allow registration to continue
            // In production, you should throw exception or use a proper email service
        } catch (Exception e) {
            logger.error("Failed to send OTP email to {}: {}", to, e.getMessage(), e);
            // Log OTP for development/testing purposes
            logger.warn("=== OTP FOR TESTING === Email: {}, OTP: {} ===", to, otp);
            // Don't throw exception - allow registration to continue in development
            // throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }
}

