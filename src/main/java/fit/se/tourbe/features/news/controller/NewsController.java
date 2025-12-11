package fit.se.tourbe.features.news.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fit.se.tourbe.features.news.dto.NewsDTO;
import fit.se.tourbe.features.news.service.NewsService;

@RestController
@RequestMapping("/news")
public class NewsController {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    
    @Autowired
    private NewsService newsService;
    
    // Create
    @PostMapping
    public ResponseEntity<?> addNews(@RequestBody NewsDTO newsDTO) {
        try {
            logger.info("Adding new news: {}", newsDTO.getTitle());
            newsService.add(newsDTO);
            return ResponseEntity.ok(newsDTO);
        } catch (Exception e) {
            logger.error("Error adding news: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add news: " + e.getMessage());
        }
    }
    
    // Read all
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAll() {
        try {
            logger.info("Getting all news");
            List<NewsDTO> newsList = newsService.getAll();
            return ResponseEntity.ok(newsList);
        } catch (Exception e) {
            logger.error("Error getting all news: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Search with pagination and filters
    @GetMapping("/search")
    public ResponseEntity<fit.se.tourbe.features.news.dto.PageResponseDTO<NewsDTO>> searchNews(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            logger.info("Searching news - keyword: {}, categoryId: {}, active: {}, page: {}, size: {}", 
                    keyword, categoryId, active, page, size);
            fit.se.tourbe.features.news.dto.PageResponseDTO<NewsDTO> result = 
                    newsService.searchNews(keyword, categoryId, active, page, size);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error searching news: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by active status
    @GetMapping("/active")
    public ResponseEntity<List<NewsDTO>> getByActive(@RequestParam(defaultValue = "true") boolean active) {
        try {
            logger.info("Getting news by active status: {}", active);
            List<NewsDTO> newsList = newsService.getByActive(active);
            return ResponseEntity.ok(newsList);
        } catch (Exception e) {
            logger.error("Error getting news by active: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<NewsDTO>> getByCategory(@PathVariable(name = "categoryId") int categoryId) {
        try {
            logger.info("Getting news by category id: {}", categoryId);
            List<NewsDTO> newsList = newsService.getByCategoryId(categoryId);
            return ResponseEntity.ok(newsList);
        } catch (Exception e) {
            logger.error("Error getting news by category: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read by featured
    @GetMapping("/featured")
    public ResponseEntity<List<NewsDTO>> getByFeatured(@RequestParam(defaultValue = "true") boolean featured) {
        try {
            logger.info("Getting featured news");
            List<NewsDTO> newsList = newsService.getByFeatured(featured);
            return ResponseEntity.ok(newsList);
        } catch (Exception e) {
            logger.error("Error getting featured news: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getOne(@PathVariable(name = "id") int id) {
        try {
            logger.info("Getting news with id: {}", id);
            NewsDTO news = newsService.getOne(id);
            if (news != null) {
                return ResponseEntity.ok(news);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting news with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Update
    @PutMapping
    public ResponseEntity<?> update(@RequestBody NewsDTO newsDTO) {
        try {
            logger.info("Updating news with id: {}", newsDTO.getId());
            newsService.update(newsDTO);
            return ResponseEntity.ok(newsDTO);
        } catch (Exception e) {
            logger.error("Error updating news with id {}: {}", newsDTO.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update news: " + e.getMessage());
        }
    }
    
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        try {
            logger.info("Deleting news with id: {}", id);
            newsService.delete(id);
            return ResponseEntity.ok("Xóa tin tức thành công");
        } catch (Exception e) {
            logger.error("Error deleting news with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete news: " + e.getMessage());
        }
    }
}

