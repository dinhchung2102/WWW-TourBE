package fit.se.tourbe.features.auth.service;

import fit.se.tourbe.features.auth.model.Customer;

public interface OtpService {
    String generateOtp();
    void saveOtpWithCustomerData(String email, String otp, Customer customer);
    boolean verifyOtp(String email, String otp);
    Customer getCustomerData(String email);
    void deleteOtp(String email);
}

