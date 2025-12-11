package fit.se.tourbe.features.promotion.service;

import fit.se.tourbe.features.promotion.models.Promotion;

public interface PromotionEmailService {
    void sendPromotionNotification(Promotion promotion);
}

