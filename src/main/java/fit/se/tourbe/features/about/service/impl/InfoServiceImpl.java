package fit.se.tourbe.features.about.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.about.dto.InfoDTO;
import fit.se.tourbe.features.about.models.Info;
import fit.se.tourbe.features.about.repository.InfoRepository;
import fit.se.tourbe.features.about.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {
    
    @Autowired
    private InfoRepository infoRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public void add(InfoDTO infoDTO) {
        Info info = modelMapper.map(infoDTO, Info.class);
        
        // Set timestamps
        Date now = new Date();
        if (info.getCreatedAt() == null) {
            info.setCreatedAt(now);
        }
        info.setUpdatedAt(now);
        
        // Nếu không có orderIndex, set mặc định là số lượng hiện tại + 1
        if (info.getOrderIndex() == null) {
            long count = infoRepository.count();
            info.setOrderIndex((int) count + 1);
        }
        
        infoRepository.save(info);
        infoDTO.setId(info.getId());
        infoDTO.setCreatedAt(info.getCreatedAt());
        infoDTO.setUpdatedAt(info.getUpdatedAt());
    }
    
    @Override
    public void update(InfoDTO infoDTO) {
        Info info = infoRepository.findById(infoDTO.getId()).orElse(null);
        if (info != null) {
            modelMapper.map(infoDTO, info);
            info.setUpdatedAt(new Date());
            infoRepository.save(info);
        }
    }
    
    @Override
    public void delete(Integer id) {
        Info info = infoRepository.findById(id).orElse(null);
        if (info != null) {
            infoRepository.delete(info);
        }
    }
    
    @Override
    public List<InfoDTO> getAll() {
        List<InfoDTO> infoDTOs = new ArrayList<>();
        infoRepository.findAll().forEach((info) -> {
            infoDTOs.add(modelMapper.map(info, InfoDTO.class));
        });
        return infoDTOs;
    }
    
    @Override
    public InfoDTO getOne(Integer id) {
        Info info = infoRepository.findById(id).orElse(null);
        if (info != null) {
            return modelMapper.map(info, InfoDTO.class);
        }
        return null;
    }
    
    @Override
    public List<InfoDTO> getByContactInfo(boolean isContactInfo) {
        List<InfoDTO> infoDTOs = new ArrayList<>();
        infoRepository.findByIsContactInfo(isContactInfo).forEach((info) -> {
            infoDTOs.add(modelMapper.map(info, InfoDTO.class));
        });
        return infoDTOs;
    }
    
    @Override
    public List<InfoDTO> getAllOrdered() {
        List<InfoDTO> infoDTOs = new ArrayList<>();
        infoRepository.findAllByOrderByOrderIndexAsc().forEach((info) -> {
            infoDTOs.add(modelMapper.map(info, InfoDTO.class));
        });
        return infoDTOs;
    }
}

