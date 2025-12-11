package fit.se.tourbe.features.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.promotion.dto.PromotionDTO;
import fit.se.tourbe.features.promotion.models.Promotion;
import fit.se.tourbe.features.promotion.repository.PromotionRepository;
import fit.se.tourbe.features.promotion.service.PromotionService;
import fit.se.tourbe.features.promotion.service.PromotionSubscriberService;
import fit.se.tourbe.features.promotion.service.PromotionEmailService;

@Service
public class PromotionServiceImpl implements PromotionService {
    
    @Autowired
    private PromotionRepository promotionRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PromotionSubscriberService promotionSubscriberService;
    
    @Autowired
    private PromotionEmailService promotionEmailService;
    
    @Override
    public PromotionDTO add(PromotionDTO promotionDTO) {
        Promotion promotion = modelMapper.map(promotionDTO, Promotion.class);
        promotion.setCreatedAt(new Date());
        promotion.setUpdatedAt(new Date());
        promotion.setActive(true);
        promotion.setUsedCount(0);
        
        promotionRepository.save(promotion);
        promotionDTO.setId(promotion.getId());
        
        // Send notification email to all active subscribers
        try {
            promotionEmailService.sendPromotionNotification(promotion);
        } catch (Exception e) {
            // Log error but don't fail the promotion creation
            System.err.println("Failed to send promotion notification emails: " + e.getMessage());
        }
        
        return promotionDTO;
    }
    
    @Override
    public void update(PromotionDTO promotionDTO) {
        Promotion promotion = promotionRepository.findById(promotionDTO.getId()).orElse(null);
        if (promotion != null) {
            modelMapper.map(promotionDTO, promotion);
            promotion.setUpdatedAt(new Date());
            promotionRepository.save(promotion);
        }
    }
    
    @Override
    public void delete(Integer id) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion != null) {
            promotionRepository.delete(promotion);
        }
    }
    
    @Override
    public List<PromotionDTO> getAll() {
        List<PromotionDTO> promotionDTOs = new ArrayList<>();
        promotionRepository.findAll().forEach((promotion) -> {
            promotionDTOs.add(modelMapper.map(promotion, PromotionDTO.class));
        });
        return promotionDTOs;
    }
    
    @Override
    public PromotionDTO getOne(Integer id) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion != null) {
            return modelMapper.map(promotion, PromotionDTO.class);
        }
        return null;
    }
    
    @Override
    public List<PromotionDTO> getByActive(boolean active) {
        List<PromotionDTO> promotionDTOs = new ArrayList<>();
        promotionRepository.findByActive(active).forEach((promotion) -> {
            promotionDTOs.add(modelMapper.map(promotion, PromotionDTO.class));
        });
        return promotionDTOs;
    }
    
    @Override
    public PromotionDTO getByCode(String code) {
        Promotion promotion = promotionRepository.findByCode(code).orElse(null);
        if (promotion != null) {
            return modelMapper.map(promotion, PromotionDTO.class);
        }
        return null;
    }
    
    @Override
    public List<PromotionDTO> getActivePromotions() {
        List<PromotionDTO> promotionDTOs = new ArrayList<>();
        promotionRepository.findActivePromotions(new Date()).forEach((promotion) -> {
            promotionDTOs.add(modelMapper.map(promotion, PromotionDTO.class));
        });
        return promotionDTOs;
    }
}

