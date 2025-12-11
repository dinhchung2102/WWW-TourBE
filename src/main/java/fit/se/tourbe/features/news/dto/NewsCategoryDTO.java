package fit.se.tourbe.features.news.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsCategoryDTO {
    private Integer id;
    private String name;
    private String description;
    private String slug;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
}

