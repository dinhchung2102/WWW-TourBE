package fit.se.tourbe.features.about.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Integer id;
    private String fullName;
    private String phone;
    private String email;
    private String description;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
}

