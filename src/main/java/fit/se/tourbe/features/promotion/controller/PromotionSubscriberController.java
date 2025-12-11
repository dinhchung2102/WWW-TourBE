package fit.se.tourbe.features.promotion.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fit.se.tourbe.features.promotion.dto.PromotionSubscriberDTO;
import fit.se.tourbe.features.promotion.service.PromotionSubscriberService;

@RestController
@RequestMapping("/promotion-subscriber")
public class PromotionSubscriberController {
    
    private static final Logger logger = LoggerFactory.getLogger(PromotionSubscriberController.class);
    
    @Autowired
    private PromotionSubscriberService promotionSubscriberService;
    
    // Subscribe
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String name = request.get("name");
            
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            
            logger.info("Subscribing email: {}", email);
            PromotionSubscriberDTO subscriber = promotionSubscriberService.subscribe(email, name);
            return ResponseEntity.ok(subscriber);
        } catch (Exception e) {
            logger.error("Error subscribing: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to subscribe: " + e.getMessage());
        }
    }
    
    // Unsubscribe by email
    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            
            logger.info("Unsubscribing email: {}", email);
            promotionSubscriberService.unsubscribe(email);
            return ResponseEntity.ok("Đã hủy đăng ký nhận khuyến mãi thành công");
        } catch (Exception e) {
            logger.error("Error unsubscribing: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to unsubscribe: " + e.getMessage());
        }
    }
    
    // Unsubscribe by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> unsubscribeById(@PathVariable(name = "id") int id) {
        try {
            logger.info("Unsubscribing subscriber with id: {}", id);
            promotionSubscriberService.unsubscribeById(id);
            return ResponseEntity.ok("Đã hủy đăng ký nhận khuyến mãi thành công");
        } catch (Exception e) {
            logger.error("Error unsubscribing subscriber with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to unsubscribe: " + e.getMessage());
        }
    }
    
    // Read all
    @GetMapping
    public ResponseEntity<List<PromotionSubscriberDTO>> getAll() {
        try {
            logger.info("Getting all subscribers");
            List<PromotionSubscriberDTO> subscribers = promotionSubscriberService.getAll();
            return ResponseEntity.ok(subscribers);
        } catch (Exception e) {
            logger.error("Error getting all subscribers: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by active status
    @GetMapping("/active")
    public ResponseEntity<List<PromotionSubscriberDTO>> getByActive(@RequestParam(defaultValue = "true") boolean active) {
        try {
            logger.info("Getting subscribers by active status: {}", active);
            List<PromotionSubscriberDTO> subscribers = promotionSubscriberService.getByActive(active);
            return ResponseEntity.ok(subscribers);
        } catch (Exception e) {
            logger.error("Error getting subscribers by active: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by email
    @GetMapping("/email/{email}")
    public ResponseEntity<PromotionSubscriberDTO> getByEmail(@PathVariable(name = "email") String email) {
        try {
            logger.info("Getting subscriber by email: {}", email);
            PromotionSubscriberDTO subscriber = promotionSubscriberService.getByEmail(email);
            if (subscriber != null) {
                return ResponseEntity.ok(subscriber);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting subscriber by email {}: {}", email, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

