package fit.se.tourbe.features.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.promotion.dto.PromotionSubscriberDTO;
import fit.se.tourbe.features.promotion.models.PromotionSubscriber;
import fit.se.tourbe.features.promotion.repository.PromotionSubscriberRepository;
import fit.se.tourbe.features.promotion.service.PromotionSubscriberService;

@Service
public class PromotionSubscriberServiceImpl implements PromotionSubscriberService {
    
    @Autowired
    private PromotionSubscriberRepository promotionSubscriberRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public PromotionSubscriberDTO subscribe(String email, String name) {
        // Check if email already exists
        Optional<PromotionSubscriber> existing = promotionSubscriberRepository.findByEmail(email);
        if (existing.isPresent()) {
            PromotionSubscriber subscriber = existing.get();
            // If unsubscribed, reactivate
            if (!subscriber.isActive()) {
                subscriber.setActive(true);
                subscriber.setUnsubscribedAt(null);
                subscriber.setName(name != null ? name : subscriber.getName());
                promotionSubscriberRepository.save(subscriber);
                return modelMapper.map(subscriber, PromotionSubscriberDTO.class);
            }
            // Already subscribed
            return modelMapper.map(subscriber, PromotionSubscriberDTO.class);
        }
        
        // Create new subscriber
        PromotionSubscriber subscriber = new PromotionSubscriber();
        subscriber.setEmail(email);
        subscriber.setName(name);
        subscriber.setActive(true);
        subscriber.setSubscribedAt(new Date());
        
        promotionSubscriberRepository.save(subscriber);
        return modelMapper.map(subscriber, PromotionSubscriberDTO.class);
    }
    
    @Override
    public void unsubscribe(String email) {
        Optional<PromotionSubscriber> subscriberOpt = promotionSubscriberRepository.findByEmail(email);
        if (subscriberOpt.isPresent()) {
            PromotionSubscriber subscriber = subscriberOpt.get();
            subscriber.setActive(false);
            subscriber.setUnsubscribedAt(new Date());
            promotionSubscriberRepository.save(subscriber);
        }
    }
    
    @Override
    public void unsubscribeById(Integer id) {
        PromotionSubscriber subscriber = promotionSubscriberRepository.findById(id).orElse(null);
        if (subscriber != null) {
            subscriber.setActive(false);
            subscriber.setUnsubscribedAt(new Date());
            promotionSubscriberRepository.save(subscriber);
        }
    }
    
    @Override
    public List<PromotionSubscriberDTO> getAll() {
        List<PromotionSubscriberDTO> subscriberDTOs = new ArrayList<>();
        promotionSubscriberRepository.findAll().forEach((subscriber) -> {
            subscriberDTOs.add(modelMapper.map(subscriber, PromotionSubscriberDTO.class));
        });
        return subscriberDTOs;
    }
    
    @Override
    public List<PromotionSubscriberDTO> getByActive(boolean active) {
        List<PromotionSubscriberDTO> subscriberDTOs = new ArrayList<>();
        promotionSubscriberRepository.findByActive(active).forEach((subscriber) -> {
            subscriberDTOs.add(modelMapper.map(subscriber, PromotionSubscriberDTO.class));
        });
        return subscriberDTOs;
    }
    
    @Override
    public PromotionSubscriberDTO getByEmail(String email) {
        PromotionSubscriber subscriber = promotionSubscriberRepository.findByEmail(email).orElse(null);
        if (subscriber != null) {
            return modelMapper.map(subscriber, PromotionSubscriberDTO.class);
        }
        return null;
    }
    
    @Override
    public List<String> getAllActiveEmails() {
        List<String> emails = new ArrayList<>();
        promotionSubscriberRepository.findByActive(true).forEach((subscriber) -> {
            emails.add(subscriber.getEmail());
        });
        return emails;
    }
}

