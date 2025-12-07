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

import fit.se.tourbe.features.about.dto.ContactDTO;
import fit.se.tourbe.features.about.service.ContactService;

@RestController
@RequestMapping("/contact")
public class ContactController {
    
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    
    @Autowired
    private ContactService contactService;
    
    // Create
    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody ContactDTO contactDTO) {
        try {
            logger.info("Adding new contact: {}", contactDTO.getFullName());
            contactService.add(contactDTO);
            return ResponseEntity.ok(contactDTO);
        } catch (Exception e) {
            logger.error("Error adding contact: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add contact: " + e.getMessage());
        }
    }
    
    // Read all
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAll() {
        try {
            logger.info("Getting all contacts");
            List<ContactDTO> contacts = contactService.getAll();
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            logger.error("Error getting all contacts: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by active status
    @GetMapping("/active")
    public ResponseEntity<List<ContactDTO>> getByActive(@RequestParam(defaultValue = "true") boolean active) {
        try {
            logger.info("Getting contacts by active status: {}", active);
            List<ContactDTO> contacts = contactService.getByActive(active);
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            logger.error("Error getting contacts by active: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getOne(@PathVariable(name = "id") int id) {
        try {
            logger.info("Getting contact with id: {}", id);
            ContactDTO contact = contactService.getOne(id);
            if (contact != null) {
                return ResponseEntity.ok(contact);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting contact with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Update
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ContactDTO contactDTO) {
        try {
            logger.info("Updating contact with id: {}", contactDTO.getId());
            contactService.update(contactDTO);
            return ResponseEntity.ok(contactDTO);
        } catch (Exception e) {
            logger.error("Error updating contact with id {}: {}", contactDTO.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update contact: " + e.getMessage());
        }
    }
    
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        try {
            logger.info("Deleting contact with id: {}", id);
            contactService.delete(id);
            return ResponseEntity.ok("Xóa contact thành công");
        } catch (Exception e) {
            logger.error("Error deleting contact with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete contact: " + e.getMessage());
        }
    }
}

