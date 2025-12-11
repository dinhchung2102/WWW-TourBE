package fit.se.tourbe.features.news.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private Integer id;
    private String title;
    private String content;
    private String summary;
    private String image;
    private String slug;
    private Integer categoryId;
    private String categoryName;
    private String author;
    private int views;
    private boolean active;
    private boolean featured;
    private Date createdAt;
    private Date updatedAt;
}

