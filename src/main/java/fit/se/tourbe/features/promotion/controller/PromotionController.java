package fit.se.tourbe.features.promotion.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fit.se.tourbe.features.promotion.dto.PromotionDTO;
import fit.se.tourbe.features.promotion.service.PromotionService;

@RestController
@RequestMapping("/promotion")
public class PromotionController {
    
    private static final Logger logger = LoggerFactory.getLogger(PromotionController.class);
    
    @Autowired
    private PromotionService promotionService;
    
    // Create
    @PostMapping
    public ResponseEntity<?> addPromotion(@RequestBody PromotionDTO promotionDTO) {
        try {
            logger.info("Adding new promotion: {}", promotionDTO.getTitle());
            PromotionDTO result = promotionService.add(promotionDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error adding promotion: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add promotion: " + e.getMessage());
        }
    }
    
    // Read all
    @GetMapping
    public ResponseEntity<List<PromotionDTO>> getAll() {
        try {
            logger.info("Getting all promotions");
            List<PromotionDTO> promotions = promotionService.getAll();
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            logger.error("Error getting all promotions: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read active promotions
    @GetMapping("/active")
    public ResponseEntity<List<PromotionDTO>> getActivePromotions() {
        try {
            logger.info("Getting active promotions");
            List<PromotionDTO> promotions = promotionService.getActivePromotions();
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            logger.error("Error getting active promotions: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by active status
    @GetMapping("/status")
    public ResponseEntity<List<PromotionDTO>> getByActive(@RequestParam(defaultValue = "true") boolean active) {
        try {
            logger.info("Getting promotions by active status: {}", active);
            List<PromotionDTO> promotions = promotionService.getByActive(active);
            return ResponseEntity.ok(promotions);
        } catch (Exception e) {
            logger.error("Error getting promotions by active: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by code
    @GetMapping("/code/{code}")
    public ResponseEntity<PromotionDTO> getByCode(@PathVariable(name = "code") String code) {
        try {
            logger.info("Getting promotion by code: {}", code);
            PromotionDTO promotion = promotionService.getByCode(code);
            if (promotion != null) {
                return ResponseEntity.ok(promotion);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting promotion by code {}: {}", code, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<PromotionDTO> getOne(@PathVariable(name = "id") int id) {
        try {
            logger.info("Getting promotion with id: {}", id);
            PromotionDTO promotion = promotionService.getOne(id);
            if (promotion != null) {
                return ResponseEntity.ok(promotion);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting promotion with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Update
    @PutMapping
    public ResponseEntity<?> update(@RequestBody PromotionDTO promotionDTO) {
        try {
            logger.info("Updating promotion with id: {}", promotionDTO.getId());
            promotionService.update(promotionDTO);
            return ResponseEntity.ok(promotionDTO);
        } catch (Exception e) {
            logger.error("Error updating promotion with id {}: {}", promotionDTO.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update promotion: " + e.getMessage());
        }
    }
    
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        try {
            logger.info("Deleting promotion with id: {}", id);
            promotionService.delete(id);
            return ResponseEntity.ok("Xóa khuyến mãi thành công");
        } catch (Exception e) {
            logger.error("Error deleting promotion with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete promotion: " + e.getMessage());
        }
    }
}

