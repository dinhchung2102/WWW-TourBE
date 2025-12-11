package fit.se.tourbe.features.promotion.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionSubscriberDTO {
    private Integer id;
    private String email;
    private String name;
    private boolean active;
    private Date subscribedAt;
    private Date unsubscribedAt;
}

