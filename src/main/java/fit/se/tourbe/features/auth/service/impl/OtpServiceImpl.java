package fit.se.tourbe.features.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fit.se.tourbe.features.auth.model.Customer;
import fit.se.tourbe.features.auth.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {
    
    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final String OTP_PREFIX = "otp:";
    private static final String CUSTOMER_DATA_PREFIX = "customer:";
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private final Random random = new Random();
    
    @Override
    public String generateOtp() {
        // Generate 6-digit OTP
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    
    @Override
    public void saveOtpWithCustomerData(String email, String otp, Customer customer) {
        String otpKey = OTP_PREFIX + email;
        String customerKey = CUSTOMER_DATA_PREFIX + email;
        try {
            // Save OTP
            redisTemplate.opsForValue().set(otpKey, otp, OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);
            
            // Save customer data (without password for security, we'll set it later)
            Customer tempCustomer = new Customer();
            tempCustomer.setName(customer.getName());
            tempCustomer.setEmail(customer.getEmail());
            tempCustomer.setPhone(customer.getPhone());
            tempCustomer.setPassword(customer.getPassword()); // Store temporarily, will be hashed on registration
            tempCustomer.setRole(customer.getRole());
            tempCustomer.setAuthProvider(customer.getAuthProvider());
            
            String customerJson = objectMapper.writeValueAsString(tempCustomer);
            redisTemplate.opsForValue().set(customerKey, customerJson, OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);
            
            logger.info("OTP and customer data saved for email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to save OTP and customer data for email {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to save OTP: " + e.getMessage());
        }
    }
    
    @Override
    public boolean verifyOtp(String email, String otp) {
        String key = OTP_PREFIX + email;
        try {
            String storedOtp = redisTemplate.opsForValue().get(key);
            if (storedOtp == null) {
                logger.warn("OTP not found or expired for email: {}", email);
                return false;
            }
            
            boolean isValid = storedOtp.equals(otp);
            if (isValid) {
                logger.info("OTP verified successfully for email: {}", email);
            } else {
                logger.warn("Invalid OTP for email: {}", email);
            }
            return isValid;
        } catch (Exception e) {
            logger.error("Failed to verify OTP for email {}: {}", email, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public Customer getCustomerData(String email) {
        String customerKey = CUSTOMER_DATA_PREFIX + email;
        try {
            String customerJson = redisTemplate.opsForValue().get(customerKey);
            if (customerJson == null) {
                logger.warn("Customer data not found for email: {}", email);
                return null;
            }
            return objectMapper.readValue(customerJson, Customer.class);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse customer data for email {}: {}", email, e.getMessage(), e);
            return null;
        }
    }
    
    @Override
    public void deleteOtp(String email) {
        String otpKey = OTP_PREFIX + email;
        String customerKey = CUSTOMER_DATA_PREFIX + email;
        try {
            redisTemplate.delete(otpKey);
            redisTemplate.delete(customerKey);
            logger.info("OTP and customer data deleted for email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to delete OTP and customer data for email {}: {}", email, e.getMessage(), e);
        }
    }
}

