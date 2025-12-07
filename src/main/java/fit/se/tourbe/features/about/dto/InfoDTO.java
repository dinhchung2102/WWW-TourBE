package fit.se.tourbe.features.about.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoDTO {
    private int id;
    private String title;
    private String description;
    private String image;
    private boolean isContactInfo;
    private Date createdAt;
    private Date updatedAt;
    private Integer orderIndex;
}

