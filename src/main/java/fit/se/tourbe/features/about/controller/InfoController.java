package fit.se.tourbe.features.about.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fit.se.tourbe.features.about.dto.InfoDTO;
import fit.se.tourbe.features.about.service.InfoService;

@RestController
@RequestMapping("/about")
public class InfoController {
    
    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);
    
    @Autowired
    private InfoService infoService;
    
    // Create
    @PostMapping("/info")
    public ResponseEntity<?> addInfo(@RequestBody InfoDTO infoDTO) {
        try {
            logger.info("Adding new info: {}", infoDTO.getTitle());
            logger.debug("InfoDTO received: {}", infoDTO);
            infoService.add(infoDTO);
            return ResponseEntity.ok(infoDTO);
        } catch (Exception e) {
            logger.error("Error adding info: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to add info: " + e.getMessage());
        }
    }
    
    // Read all
    @GetMapping("/info")
    public ResponseEntity<List<InfoDTO>> getAll() {
        try {
            logger.info("Getting all info");
            List<InfoDTO> infos = infoService.getAll();
            return ResponseEntity.ok(infos);
        } catch (Exception e) {
            logger.error("Error getting all info: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read all ordered by orderIndex
    @GetMapping("/info/ordered")
    public ResponseEntity<List<InfoDTO>> getAllOrdered() {
        try {
            logger.info("Getting all info ordered by orderIndex");
            List<InfoDTO> infos = infoService.getAllOrdered();
            return ResponseEntity.ok(infos);
        } catch (Exception e) {
            logger.error("Error getting ordered info: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by contact info
    @GetMapping("/info/contact")
    public ResponseEntity<List<InfoDTO>> getByContactInfo(@RequestParam(defaultValue = "true") boolean isContactInfo) {
        try {
            logger.info("Getting info by contactInfo: {}", isContactInfo);
            List<InfoDTO> infos = infoService.getByContactInfo(isContactInfo);
            return ResponseEntity.ok(infos);
        } catch (Exception e) {
            logger.error("Error getting info by contactInfo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read one
    @GetMapping("/info/{id}")
    public ResponseEntity<InfoDTO> getOne(@PathVariable(name = "id") int id) {
        try {
            logger.info("Getting info with id: {}", id);
            InfoDTO info = infoService.getOne(id);
            if (info != null) {
                return ResponseEntity.ok(info);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting info with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Update
    @PutMapping("/info")
    public ResponseEntity<?> update(@RequestBody InfoDTO infoDTO) {
        try {
            logger.info("Updating info with id: {}", infoDTO.getId());
            infoService.update(infoDTO);
            return ResponseEntity.ok(infoDTO);
        } catch (Exception e) {
            logger.error("Error updating info with id {}: {}", infoDTO.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update info: " + e.getMessage());
        }
    }
    
    // Delete
    @DeleteMapping("/info/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        try {
            logger.info("Deleting info with id: {}", id);
            infoService.delete(id);
            return ResponseEntity.ok("Xóa info thành công");
        } catch (Exception e) {
            logger.error("Error deleting info with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete info: " + e.getMessage());
        }
    }
}

