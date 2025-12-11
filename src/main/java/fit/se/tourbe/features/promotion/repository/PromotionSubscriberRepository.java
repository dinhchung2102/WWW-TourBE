package fit.se.tourbe.features.promotion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.se.tourbe.features.promotion.models.PromotionSubscriber;

@Repository
public interface PromotionSubscriberRepository extends JpaRepository<PromotionSubscriber, Integer> {
    
    Optional<PromotionSubscriber> findByEmail(String email);
    
    List<PromotionSubscriber> findByActive(boolean active);
}

