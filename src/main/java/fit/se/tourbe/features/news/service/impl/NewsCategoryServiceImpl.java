package fit.se.tourbe.features.news.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.news.dto.NewsCategoryDTO;
import fit.se.tourbe.features.news.models.NewsCategory;
import fit.se.tourbe.features.news.repository.NewsCategoryRepository;
import fit.se.tourbe.features.news.service.NewsCategoryService;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {
    
    @Autowired
    private NewsCategoryRepository newsCategoryRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public void add(NewsCategoryDTO newsCategoryDTO) {
        NewsCategory newsCategory = modelMapper.map(newsCategoryDTO, NewsCategory.class);
        newsCategory.setCreatedAt(new Date());
        newsCategory.setUpdatedAt(new Date());
        newsCategory.setActive(true);
        
        newsCategoryRepository.save(newsCategory);
        newsCategoryDTO.setId(newsCategory.getId());
    }
    
    @Override
    public void update(NewsCategoryDTO newsCategoryDTO) {
        NewsCategory newsCategory = newsCategoryRepository.findById(newsCategoryDTO.getId()).orElse(null);
        if (newsCategory != null) {
            modelMapper.map(newsCategoryDTO, newsCategory);
            newsCategory.setUpdatedAt(new Date());
            newsCategoryRepository.save(newsCategory);
        }
    }
    
    @Override
    public void delete(Integer id) {
        NewsCategory newsCategory = newsCategoryRepository.findById(id).orElse(null);
        if (newsCategory != null) {
            newsCategoryRepository.delete(newsCategory);
        }
    }
    
    @Override
    public List<NewsCategoryDTO> getAll() {
        List<NewsCategoryDTO> newsCategoryDTOs = new ArrayList<>();
        newsCategoryRepository.findAll().forEach((newsCategory) -> {
            newsCategoryDTOs.add(modelMapper.map(newsCategory, NewsCategoryDTO.class));
        });
        return newsCategoryDTOs;
    }
    
    @Override
    public NewsCategoryDTO getOne(Integer id) {
        NewsCategory newsCategory = newsCategoryRepository.findById(id).orElse(null);
        if (newsCategory != null) {
            return modelMapper.map(newsCategory, NewsCategoryDTO.class);
        }
        return null;
    }
    
    @Override
    public List<NewsCategoryDTO> getByActive(boolean active) {
        List<NewsCategoryDTO> newsCategoryDTOs = new ArrayList<>();
        newsCategoryRepository.findByActive(active).forEach((newsCategory) -> {
            newsCategoryDTOs.add(modelMapper.map(newsCategory, NewsCategoryDTO.class));
        });
        return newsCategoryDTOs;
    }
}

