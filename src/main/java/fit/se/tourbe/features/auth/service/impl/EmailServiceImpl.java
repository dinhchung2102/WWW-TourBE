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
        } catch (Exception e) {
            logger.error("Failed to send OTP email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }
}

