package fit.se.tourbe.features.promotion.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class EmailTemplateUtil {
    
    /**
     * Load HTML template from resources and replace placeholders
     */
    public static String loadTemplate(String templatePath, Map<String, String> variables) {
        try {
            ClassPathResource resource = new ClassPathResource(templatePath);
            InputStream inputStream = resource.getInputStream();
            String template = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            
            // First, remove conditional blocks that are not needed
            template = removeUnusedConditionalBlocks(template, variables);
            
            // Then replace all placeholders
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                String value = entry.getValue() != null ? entry.getValue() : "";
                template = template.replace(placeholder, value);
            }
            
            return template;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template: " + templatePath, e);
        }
    }
    
    /**
     * Remove conditional blocks ({{#VAR}}...{{/VAR}}) if variable is null or empty
     */
    private static String removeUnusedConditionalBlocks(String template, Map<String, String> variables) {
        // Remove blocks like {{#DESCRIPTION}}...{{/DESCRIPTION}} if DESCRIPTION is empty
        // This is a simple implementation - for production, consider using a proper template engine
        String result = template;
        // Match {{#VAR}}...{{/VAR}} patterns and remove if VAR is empty
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
            "\\{\\{#([^}]+)\\}\\}([\\s\\S]*?)\\{\\{/\\1\\}\\}", 
            java.util.regex.Pattern.DOTALL
        );
        java.util.regex.Matcher matcher = pattern.matcher(result);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String varName = matcher.group(1);
            String blockContent = matcher.group(2);
            // Check if variable exists and is not empty
            boolean shouldKeep = variables.containsKey(varName) && 
                                 variables.get(varName) != null && 
                                 !variables.get(varName).trim().isEmpty();
            if (shouldKeep) {
                matcher.appendReplacement(sb, blockContent);
            } else {
                matcher.appendReplacement(sb, "");
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    /**
     * Build promotion email HTML content
     */
    public static String buildPromotionEmailHtml(
            String title,
            String description,
            double discountPercent,
            String code,
            String startDate,
            String endDate,
            String minOrderAmount,
            String maxDiscountAmount) {
        
        Map<String, String> variables = new HashMap<>();
        variables.put("TITLE", title != null ? title : "");
        variables.put("DESCRIPTION", description != null ? description : "");
        variables.put("DISCOUNT_PERCENT", String.format("%.0f", discountPercent));
        variables.put("CODE", code != null ? code : "");
        variables.put("START_DATE", startDate != null ? startDate : "");
        variables.put("END_DATE", endDate != null ? endDate : "");
        variables.put("MIN_ORDER_AMOUNT", minOrderAmount != null ? minOrderAmount : "");
        variables.put("MAX_DISCOUNT_AMOUNT", maxDiscountAmount != null ? maxDiscountAmount : "");
        
        return loadTemplate("templates/promotion-email.html", variables);
    }
    
    /**
     * Build OTP email HTML content
     */
    public static String buildOtpEmailHtml(String otp) {
        Map<String, String> variables = new HashMap<>();
        variables.put("OTP", otp != null ? otp : "");
        
        return loadTemplate("templates/otp-email.html", variables);
    }
}

