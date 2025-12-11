package fit.se.tourbe.features.auth.service.impl;

import fit.se.tourbe.features.auth.model.Customer;
import fit.se.tourbe.features.auth.repository.CustomerRepository;
import fit.se.tourbe.features.auth.service.CustomerService;
import fit.se.tourbe.features.auth.service.DatabaseService;
import fit.se.tourbe.features.auth.service.EmailService;
import fit.se.tourbe.features.auth.service.OtpService;
import fit.se.tourbe.features.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DatabaseService databaseService;
    
    @Autowired
    private OtpService otpService;
    
    @Autowired
    private EmailService emailService;

    @Override
    public Customer registerCustomer(Customer customer) {
        // Mã hóa mật khẩu trước khi lưu vào database
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return databaseService.executeWithRetry(() -> customerRepository.save(customer));
    }
    
    @Override
    public void sendOtpForRegistration(Customer customer) {
        // Check if email already exists
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        
        // Generate OTP
        String otp = otpService.generateOtp();
        
        // Save OTP and customer data to Redis
        otpService.saveOtpWithCustomerData(customer.getEmail(), otp, customer);
        
        // Send OTP via email
        emailService.sendOtpEmail(customer.getEmail(), otp);
    }
    
    @Override
    public Customer verifyOtpAndRegister(String email, String otp) {
        // Verify OTP
        if (!otpService.verifyOtp(email, otp)) {
            throw new RuntimeException("Invalid or expired OTP");
        }
        
        // Get customer data from Redis
        Customer customer = otpService.getCustomerData(email);
        if (customer == null) {
            throw new RuntimeException("Customer data not found. Please register again.");
        }
        
        // Delete OTP and customer data from Redis
        otpService.deleteOtp(email);
        
        // Hash password before saving
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setEmailVerified(true);
        
        // Save customer to database
        return databaseService.executeWithRetry(() -> customerRepository.save(customer));
    }

    @Override
    public Customer findByEmail(String email) {
        return databaseService.executeWithRetry(() -> customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email)));
    }

    @Override
    public Customer findByPhone(String phone) {
        return databaseService.executeWithRetry(() -> customerRepository.findByPhone(phone).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("User not found with phone: " + phone)));
    }

    @Override
    public List<Customer> findAllCustomers() {
        return databaseService.executeWithRetry(() -> customerRepository.findAll());
    }

    @Override
    public Customer updateCustomer(String token, Customer updatedCustomer) {
        try {
            String currentUserEmail = jwtUtil.extractUsername(token);
            System.out.println("currentUserEmail: " + currentUserEmail);
            if (currentUserEmail == null || currentUserEmail.isEmpty()) {
                throw new RuntimeException("Invalid token");
            }

            return databaseService.executeWithRetry(() -> {
                Customer existingCustomer = customerRepository.findByEmail(currentUserEmail)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // Chỉ cho phép cập nhật nếu email trong token khớp với email của khách hàng
                if (!existingCustomer.getEmail().equals(updatedCustomer.getEmail())) {
                    throw new RuntimeException("Unauthorized to update this account");
                }

                // Chỉ cập nhật những thông tin cần thiết (tránh ghi đè password)
                existingCustomer.setName(updatedCustomer.getName());
                existingCustomer.setPhone(updatedCustomer.getPhone());

                return customerRepository.save(existingCustomer);
            });
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    @Override
    public void deleteCustomer(Integer id) {
        databaseService.executeWithRetryNoReturn(() -> customerRepository.deleteById(id));
    }

    @Override
    public void resetPassword(Integer id) {
        databaseService.executeWithRetryNoReturn(() -> {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            customer.setPassword(passwordEncoder.encode("12345")); // Reset password về 12345
            customerRepository.save(customer);
        });
    }

    @Override
    public void changePassword(String token, String oldPassword, String newPassword) {
        try {
            // Kiểm tra token hợp lệ
            if (token == null || token.isEmpty()) {
                throw new RuntimeException("Invalid token format");
            }

            // Lấy email từ JWT
            String email;
            try {
                email = jwtUtil.extractUsername(token.replace("Bearer ", ""));
            } catch (Exception e) {
                throw new RuntimeException("Invalid or expired token");
            }

            databaseService.executeWithRetryNoReturn(() -> {
                // Tìm người dùng
                Customer customer = findByEmail(email);

                if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
                    throw new RuntimeException("Old password is incorrect");
                }
                customer.setPassword(passwordEncoder.encode(newPassword));
                customerRepository.save(customer);
            });
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new RuntimeException("Token has expired, please login again.");
        } catch (io.jsonwebtoken.SignatureException e) {
            throw new RuntimeException("Invalid JWT signature.");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token format.");
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return databaseService.executeWithRetry(() -> customerRepository.save(customer));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = findByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                customer.getEmail(), customer.getPassword(), customer.getAuthorities());
    }

    @Override
    public Map<String, String> generateTokens(UserDetails userDetails) {
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        // Lưu refreshToken vào database
        return databaseService.executeWithRetry(() -> {
            Customer customer = findByEmail(userDetails.getUsername());
            customer.setRefreshToken(refreshToken);
            customerRepository.save(customer);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        });
    }

}