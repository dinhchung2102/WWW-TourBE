package fit.se.tourbe.features.news.service;

import java.util.List;

import fit.se.tourbe.features.news.dto.NewsDTO;
import fit.se.tourbe.features.news.dto.PageResponseDTO;

public interface NewsService {
    void add(NewsDTO newsDTO);
    void update(NewsDTO newsDTO);
    void delete(Integer id);
    List<NewsDTO> getAll();
    NewsDTO getOne(Integer id);
    List<NewsDTO> getByActive(boolean active);
    List<NewsDTO> getByCategoryId(Integer categoryId);
    List<NewsDTO> getByFeatured(boolean featured);
    
    // Search with pagination and filters
    PageResponseDTO<NewsDTO> searchNews(String keyword, Integer categoryId, Boolean active, int page, int size);
}

