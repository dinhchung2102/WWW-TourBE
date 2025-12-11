package fit.se.tourbe.features.auth.service;

public interface EmailService {
    void sendOtpEmail(String to, String otp);
}

