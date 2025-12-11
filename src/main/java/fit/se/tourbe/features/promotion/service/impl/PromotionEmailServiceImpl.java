package fit.se.tourbe.features.promotion.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.promotion.models.Promotion;
import fit.se.tourbe.features.promotion.service.PromotionEmailService;
import fit.se.tourbe.features.promotion.service.PromotionSubscriberService;

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
        
        // Build email content
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Xin chào,\n\n");
        emailContent.append("Chúng tôi có khuyến mãi mới dành cho bạn!\n\n");
        emailContent.append("Tiêu đề: ").append(promotion.getTitle()).append("\n");
        if (promotion.getDescription() != null && !promotion.getDescription().isEmpty()) {
            emailContent.append("Mô tả: ").append(promotion.getDescription()).append("\n");
        }
        emailContent.append("Giảm giá: ").append(promotion.getDiscountPercent()).append("%\n");
        if (promotion.getCode() != null && !promotion.getCode().isEmpty()) {
            emailContent.append("Mã khuyến mãi: ").append(promotion.getCode()).append("\n");
        }
        emailContent.append("Thời gian: Từ ").append(startDateStr).append(" đến ").append(endDateStr).append("\n");
        if (promotion.getMinOrderAmount() != null) {
            emailContent.append("Áp dụng cho đơn hàng từ: ").append(String.format("%.0f", promotion.getMinOrderAmount())).append(" VNĐ\n");
        }
        emailContent.append("\n");
        emailContent.append("Hãy nhanh tay đặt tour để nhận ưu đãi này!\n\n");
        emailContent.append("Trân trọng,\n");
        emailContent.append("Tour Booking Team");
        
        // Send email to all subscribers
        int successCount = 0;
        int failCount = 0;
        
        for (String email : subscriberEmails) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(email);
                message.setSubject("Khuyến mãi mới - " + promotion.getTitle());
                message.setText(emailContent.toString());
                
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

