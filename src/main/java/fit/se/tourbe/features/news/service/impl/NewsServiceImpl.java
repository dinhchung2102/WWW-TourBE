package fit.se.tourbe.features.news.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.news.dto.NewsDTO;
import fit.se.tourbe.features.news.dto.PageResponseDTO;
import fit.se.tourbe.features.news.models.News;
import fit.se.tourbe.features.news.models.NewsCategory;
import fit.se.tourbe.features.news.repository.NewsRepository;
import fit.se.tourbe.features.news.repository.NewsCategoryRepository;
import fit.se.tourbe.features.news.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
    
    @Autowired
    private NewsRepository newsRepository;
    
    @Autowired
    private NewsCategoryRepository newsCategoryRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public void add(NewsDTO newsDTO) {
        News news = modelMapper.map(newsDTO, News.class);
        
        // Set category
        if (newsDTO.getCategoryId() != null) {
            NewsCategory category = newsCategoryRepository.findById(newsDTO.getCategoryId()).orElse(null);
            if (category != null) {
                news.setCategory(category);
            }
        }
        
        news.setCreatedAt(new Date());
        news.setUpdatedAt(new Date());
        news.setActive(true);
        news.setViews(0);
        
        newsRepository.save(news);
        newsDTO.setId(news.getId());
    }
    
    @Override
    public void update(NewsDTO newsDTO) {
        News news = newsRepository.findById(newsDTO.getId()).orElse(null);
        if (news != null) {
            // Update basic fields
            news.setTitle(newsDTO.getTitle());
            news.setContent(newsDTO.getContent());
            news.setSummary(newsDTO.getSummary());
            news.setImage(newsDTO.getImage());
            news.setSlug(newsDTO.getSlug());
            news.setAuthor(newsDTO.getAuthor());
            news.setActive(newsDTO.isActive());
            news.setFeatured(newsDTO.isFeatured());
            
            // Update category if changed
            if (newsDTO.getCategoryId() != null && 
                (news.getCategory() == null || news.getCategory().getId() != newsDTO.getCategoryId())) {
                NewsCategory category = newsCategoryRepository.findById(newsDTO.getCategoryId()).orElse(null);
                if (category != null) {
                    news.setCategory(category);
                }
            }
            
            news.setUpdatedAt(new Date());
            newsRepository.save(news);
        }
    }
    
    @Override
    public void delete(Integer id) {
        News news = newsRepository.findById(id).orElse(null);
        if (news != null) {
            newsRepository.delete(news);
        }
    }
    
    @Override
    public List<NewsDTO> getAll() {
        List<NewsDTO> newsDTOs = new ArrayList<>();
        newsRepository.findAll().forEach((news) -> {
            NewsDTO dto = convertToDTO(news);
            newsDTOs.add(dto);
        });
        return newsDTOs;
    }
    
    @Override
    public NewsDTO getOne(Integer id) {
        News news = newsRepository.findById(id).orElse(null);
        if (news != null) {
            return convertToDTO(news);
        }
        return null;
    }
    
    @Override
    public List<NewsDTO> getByActive(boolean active) {
        List<NewsDTO> newsDTOs = new ArrayList<>();
        newsRepository.findByActive(active).forEach((news) -> {
            newsDTOs.add(convertToDTO(news));
        });
        return newsDTOs;
    }
    
    @Override
    public List<NewsDTO> getByCategoryId(Integer categoryId) {
        List<NewsDTO> newsDTOs = new ArrayList<>();
        newsRepository.findByCategoryId(categoryId).forEach((news) -> {
            newsDTOs.add(convertToDTO(news));
        });
        return newsDTOs;
    }
    
    @Override
    public List<NewsDTO> getByFeatured(boolean featured) {
        List<NewsDTO> newsDTOs = new ArrayList<>();
        newsRepository.findByFeatured(featured).forEach((news) -> {
            newsDTOs.add(convertToDTO(news));
        });
        return newsDTOs;
    }
    
    @Override
    public PageResponseDTO<NewsDTO> searchNews(String keyword, Integer categoryId, Boolean active, int page, int size) {
        // Normalize keyword - if empty or null, set to null
        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        
        // Create pageable
        Pageable pageable = PageRequest.of(page, size);
        
        // Search with filters
        Page<News> newsPage = newsRepository.searchNews(searchKeyword, categoryId, active, pageable);
        
        // Convert to DTOs
        List<NewsDTO> newsDTOs = newsPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        // Create page response
        PageResponseDTO<NewsDTO> pageResponse = new PageResponseDTO<>();
        pageResponse.setContent(newsDTOs);
        pageResponse.setPage(newsPage.getNumber());
        pageResponse.setSize(newsPage.getSize());
        pageResponse.setTotalElements(newsPage.getTotalElements());
        pageResponse.setTotalPages(newsPage.getTotalPages());
        pageResponse.setFirst(newsPage.isFirst());
        pageResponse.setLast(newsPage.isLast());
        
        return pageResponse;
    }
    
    private NewsDTO convertToDTO(News news) {
        NewsDTO dto = modelMapper.map(news, NewsDTO.class);
        if (news.getCategory() != null) {
            dto.setCategoryId(news.getCategory().getId());
            dto.setCategoryName(news.getCategory().getName());
        }
        return dto;
    }
}

