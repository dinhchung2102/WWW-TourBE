package fit.se.tourbe.features.auth.service.impl;

import fit.se.tourbe.features.auth.service.EmailService;
import fit.se.tourbe.features.promotion.util.EmailTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

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
            
            // Build HTML email content using template
            String htmlContent = EmailTemplateUtil.buildOtpEmailHtml(otp);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("🔐 Xác thực email đăng ký - Tour Booking");
            helper.setText(htmlContent, true); // true = HTML content
            
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

