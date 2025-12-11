package fit.se.tourbe.features.promotion.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fit.se.tourbe.features.promotion.models.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    
    List<Promotion> findByActive(boolean active);
    
    Optional<Promotion> findByCode(String code);
    
    @Query("SELECT p FROM Promotion p WHERE p.active = true AND p.startDate <= :currentDate AND p.endDate >= :currentDate")
    List<Promotion> findActivePromotions(@Param("currentDate") Date currentDate);
    
    @Query("SELECT p FROM Promotion p WHERE p.code = :code AND p.active = true AND p.startDate <= :currentDate AND p.endDate >= :currentDate")
    Optional<Promotion> findValidPromotionByCode(@Param("code") String code, @Param("currentDate") Date currentDate);
}

