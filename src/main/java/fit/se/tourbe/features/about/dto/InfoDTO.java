package fit.se.tourbe.features.about.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoDTO {
    private Integer id;
    private String title;
    private String description;
    private String image;
    
    @JsonProperty("isContactInfo")
    private boolean isContactInfo;
    
    private Date createdAt;
    private Date updatedAt;
    private Integer orderIndex;
}

