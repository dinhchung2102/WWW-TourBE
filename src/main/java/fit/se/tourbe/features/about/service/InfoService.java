package fit.se.tourbe.features.about.service;

import java.util.List;

import fit.se.tourbe.features.about.dto.InfoDTO;

public interface InfoService {
    void add(InfoDTO infoDTO);
    void update(InfoDTO infoDTO);
    void delete(Integer id);
    List<InfoDTO> getAll();
    InfoDTO getOne(Integer id);
    List<InfoDTO> getByContactInfo(boolean isContactInfo);
    List<InfoDTO> getAllOrdered();
}

