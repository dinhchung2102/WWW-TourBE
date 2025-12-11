package fit.se.tourbe.features.news.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.se.tourbe.features.news.models.NewsCategory;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer> {
    
    List<NewsCategory> findByActive(boolean active);
    
    Optional<NewsCategory> findBySlug(String slug);
}

