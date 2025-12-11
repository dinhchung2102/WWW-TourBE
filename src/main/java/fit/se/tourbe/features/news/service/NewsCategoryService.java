package fit.se.tourbe.features.news.service;

import java.util.List;

import fit.se.tourbe.features.news.dto.NewsCategoryDTO;

public interface NewsCategoryService {
    void add(NewsCategoryDTO newsCategoryDTO);
    void update(NewsCategoryDTO newsCategoryDTO);
    void delete(Integer id);
    List<NewsCategoryDTO> getAll();
    NewsCategoryDTO getOne(Integer id);
    List<NewsCategoryDTO> getByActive(boolean active);
}

