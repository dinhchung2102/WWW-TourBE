package fit.se.tourbe.features.about.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.se.tourbe.features.about.models.Info;

@Repository
public interface InfoRepository extends JpaRepository<Info, Integer> {
    
    List<Info> findByIsContactInfo(boolean isContactInfo);
    
    List<Info> findAllByOrderByOrderIndexAsc();
}

