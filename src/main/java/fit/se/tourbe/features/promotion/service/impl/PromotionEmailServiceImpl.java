package fit.se.tourbe.features.promotion.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.promotion.models.Promotion;
import fit.se.tourbe.features.promotion.service.PromotionEmailService;
import fit.se.tourbe.features.promotion.service.PromotionSubscriberService;
import fit.se.tourbe.features.promotion.util.EmailTemplateUtil;
import jakarta.mail.internet.MimeMessage;

@Service
public class PromotionEmailServiceImpl implements PromotionEmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(PromotionEmailServiceImpl.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private PromotionSubscriberService promotionSubscriberService;
    
    @Value("${spring.mail.username:}")
    private String fromEmail;
    
    @Override
    public void sendPromotionNotification(Promotion promotion) {
        // Get all active subscriber emails
        List<String> subscriberEmails = promotionSubscriberService.getAllActiveEmails();
        
        if (subscriberEmails.isEmpty()) {
            logger.info("No active subscribers to notify about promotion: {}", promotion.getTitle());
            return;
        }
        
        // Check if email is configured
        if (fromEmail == null || fromEmail.isEmpty() || fromEmail.equals("your-email@gmail.com")) {
            logger.warn("Email service not configured. Promotion notification would be sent to {} subscribers", subscriberEmails.size());
            logger.warn("Promotion details - Title: {}, Code: {}, Discount: {}%", 
                    promotion.getTitle(), promotion.getCode(), promotion.getDiscountPercent());
            return;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDateStr = promotion.getStartDate() != null ? dateFormat.format(promotion.getStartDate()) : "N/A";
        String endDateStr = promotion.getEndDate() != null ? dateFormat.format(promotion.getEndDate()) : "N/A";
        
        // Format amounts
        String minOrderAmountStr = promotion.getMinOrderAmount() != null 
                ? String.format("%.0f", promotion.getMinOrderAmount()) : null;
        String maxDiscountAmountStr = promotion.getMaxDiscountAmount() != null 
                ? String.format("%.0f", promotion.getMaxDiscountAmount()) : null;
        
        // Build HTML email content using template
        String htmlContent = EmailTemplateUtil.buildPromotionEmailHtml(
                promotion.getTitle(),
                promotion.getDescription(),
                promotion.getDiscountPercent(),
                promotion.getCode(),
                startDateStr,
                endDateStr,
                minOrderAmountStr,
                maxDiscountAmountStr
        );
        
        // Send email to all subscribers
        int successCount = 0;
        int failCount = 0;
        
        for (String email : subscriberEmails) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                
                helper.setFrom(fromEmail);
                helper.setTo(email);
                helper.setSubject("🎉 Khuyến mãi mới - " + promotion.getTitle());
                helper.setText(htmlContent, true); // true = HTML content
                
                mailSender.send(message);
                successCount++;
                logger.debug("Promotion notification sent to: {}", email);
            } catch (Exception e) {
                failCount++;
                logger.error("Failed to send promotion notification to {}: {}", email, e.getMessage());
            }
        }
        
        logger.info("Promotion notification sent - Success: {}, Failed: {}, Total: {}", 
                successCount, failCount, subscriberEmails.size());
    }
}

