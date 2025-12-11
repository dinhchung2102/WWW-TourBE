package fit.se.tourbe.features.news.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fit.se.tourbe.features.news.dto.NewsCategoryDTO;
import fit.se.tourbe.features.news.service.NewsCategoryService;

@RestController
@RequestMapping("/news-category")
public class NewsCategoryController {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsCategoryController.class);
    
    @Autowired
    private NewsCategoryService newsCategoryService;
    
    // Create
    @PostMapping
    public ResponseEntity<?> addNewsCategory(@RequestBody NewsCategoryDTO newsCategoryDTO) {
        try {
            logger.info("Adding new news category: {}", newsCategoryDTO.getName());
            newsCategoryService.add(newsCategoryDTO);
            return ResponseEntity.ok(newsCategoryDTO);
        } catch (Exception e) {
            logger.error("Error adding news category: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add news category: " + e.getMessage());
        }
    }
    
    // Read all
    @GetMapping
    public ResponseEntity<List<NewsCategoryDTO>> getAll() {
        try {
            logger.info("Getting all news categories");
            List<NewsCategoryDTO> newsCategories = newsCategoryService.getAll();
            return ResponseEntity.ok(newsCategories);
        } catch (Exception e) {
            logger.error("Error getting all news categories: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by active status
    @GetMapping("/active")
    public ResponseEntity<List<NewsCategoryDTO>> getByActive(@RequestParam(defaultValue = "true") boolean active) {
        try {
            logger.info("Getting news categories by active status: {}", active);
            List<NewsCategoryDTO> newsCategories = newsCategoryService.getByActive(active);
            return ResponseEntity.ok(newsCategories);
        } catch (Exception e) {
            logger.error("Error getting news categories by active: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<NewsCategoryDTO> getOne(@PathVariable(name = "id") int id) {
        try {
            logger.info("Getting news category with id: {}", id);
            NewsCategoryDTO newsCategory = newsCategoryService.getOne(id);
            if (newsCategory != null) {
                return ResponseEntity.ok(newsCategory);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting news category with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Update
    @PutMapping
    public ResponseEntity<?> update(@RequestBody NewsCategoryDTO newsCategoryDTO) {
        try {
            logger.info("Updating news category with id: {}", newsCategoryDTO.getId());
            newsCategoryService.update(newsCategoryDTO);
            return ResponseEntity.ok(newsCategoryDTO);
        } catch (Exception e) {
            logger.error("Error updating news category with id {}: {}", newsCategoryDTO.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update news category: " + e.getMessage());
        }
    }
    
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        try {
            logger.info("Deleting news category with id: {}", id);
            newsCategoryService.delete(id);
            return ResponseEntity.ok("Xóa danh mục tin tức thành công");
        } catch (Exception e) {
            logger.error("Error deleting news category with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete news category: " + e.getMessage());
        }
    }
}

