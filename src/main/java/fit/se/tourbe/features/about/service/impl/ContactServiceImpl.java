package fit.se.tourbe.features.about.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.about.dto.ContactDTO;
import fit.se.tourbe.features.about.models.Contact;
import fit.se.tourbe.features.about.repository.ContactRepository;
import fit.se.tourbe.features.about.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public void add(ContactDTO contactDTO) {
        Contact contact = modelMapper.map(contactDTO, Contact.class);
        contact.setCreatedAt(new Date());
        contact.setUpdatedAt(new Date());
        contact.setActive(true); // Mặc định active = true
        
        contactRepository.save(contact);
        contactDTO.setId(contact.getId());
    }
    
    @Override
    public void update(ContactDTO contactDTO) {
        Contact contact = contactRepository.findById(contactDTO.getId()).orElse(null);
        if (contact != null) {
            modelMapper.map(contactDTO, contact);
            contact.setUpdatedAt(new Date());
            contactRepository.save(contact);
        }
    }
    
    @Override
    public void delete(Integer id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null) {
            contactRepository.delete(contact);
        }
    }
    
    @Override
    public List<ContactDTO> getAll() {
        List<ContactDTO> contactDTOs = new ArrayList<>();
        contactRepository.findAll().forEach((contact) -> {
            contactDTOs.add(modelMapper.map(contact, ContactDTO.class));
        });
        return contactDTOs;
    }
    
    @Override
    public ContactDTO getOne(Integer id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null) {
            return modelMapper.map(contact, ContactDTO.class);
        }
        return null;
    }
    
    @Override
    public List<ContactDTO> getByActive(boolean active) {
        List<ContactDTO> contactDTOs = new ArrayList<>();
        contactRepository.findByActive(active).forEach((contact) -> {
            contactDTOs.add(modelMapper.map(contact, ContactDTO.class));
        });
        return contactDTOs;
    }
}

