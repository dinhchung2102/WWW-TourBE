package fit.se.tourbe.features.promotion.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
    private Integer id;
    private String title;
    private String description;
    private double discountPercent;
    private Double discountAmount;
    private String code;
    private Date startDate;
    private Date endDate;
    private Double minOrderAmount;
    private Double maxDiscountAmount;
    private Integer usageLimit;
    private int usedCount;
    private boolean active;
    private String image;
    private Date createdAt;
    private Date updatedAt;
}

