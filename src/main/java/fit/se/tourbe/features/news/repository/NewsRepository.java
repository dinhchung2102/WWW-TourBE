package fit.se.tourbe.features.news.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.se.tourbe.features.news.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    
    List<News> findByActive(boolean active);
    
    List<News> findByCategoryId(int categoryId);
    
    List<News> findByFeatured(boolean featured);
    
    Optional<News> findBySlug(String slug);
    
    List<News> findByCategoryIdAndActive(int categoryId, boolean active);
}

