package fit.se.tourbe.features.about.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fit.se.tourbe.features.about.models.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    
    List<Contact> findByActive(boolean active);
    
    List<Contact> findByEmail(String email);
}

