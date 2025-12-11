package fit.se.tourbe.features.promotion.service;

import java.util.List;

import fit.se.tourbe.features.promotion.dto.PromotionDTO;

public interface PromotionService {
    PromotionDTO add(PromotionDTO promotionDTO);
    void update(PromotionDTO promotionDTO);
    void delete(Integer id);
    List<PromotionDTO> getAll();
    PromotionDTO getOne(Integer id);
    List<PromotionDTO> getByActive(boolean active);
    PromotionDTO getByCode(String code);
    List<PromotionDTO> getActivePromotions();
}

