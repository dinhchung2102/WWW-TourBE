package fit.se.tourbe.features.promotion.service;

import java.util.List;

import fit.se.tourbe.features.promotion.dto.PromotionSubscriberDTO;

public interface PromotionSubscriberService {
    PromotionSubscriberDTO subscribe(String email, String name);
    void unsubscribe(String email);
    void unsubscribeById(Integer id);
    List<PromotionSubscriberDTO> getAll();
    List<PromotionSubscriberDTO> getByActive(boolean active);
    PromotionSubscriberDTO getByEmail(String email);
    List<String> getAllActiveEmails(); // Get all active subscriber emails for sending notifications
}

