package fit.se.tourbe.features.news.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fit.se.tourbe.features.news.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    
    List<News> findByActive(boolean active);
    
    List<News> findByCategoryId(int categoryId);
    
    List<News> findByFeatured(boolean featured);
    
    Optional<News> findBySlug(String slug);
    
    List<News> findByCategoryIdAndActive(int categoryId, boolean active);
    
    // Search with pagination and filters
    @Query("SELECT n FROM News n WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(n.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:categoryId IS NULL OR n.category.id = :categoryId) AND " +
           "(:active IS NULL OR n.active = :active)")
    Page<News> searchNews(
        @Param("keyword") String keyword,
        @Param("categoryId") Integer categoryId,
        @Param("active") Boolean active,
        Pageable pageable
    );
}

