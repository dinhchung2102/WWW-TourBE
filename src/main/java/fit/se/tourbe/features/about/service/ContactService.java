package fit.se.tourbe.features.about.service;

import java.util.List;

import fit.se.tourbe.features.about.dto.ContactDTO;

public interface ContactService {
    void add(ContactDTO contactDTO);
    void update(ContactDTO contactDTO);
    void delete(Integer id);
    List<ContactDTO> getAll();
    ContactDTO getOne(Integer id);
    List<ContactDTO> getByActive(boolean active);
}

