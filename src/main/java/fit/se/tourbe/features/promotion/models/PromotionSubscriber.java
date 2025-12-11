package fit.se.tourbe.features.promotion.models;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "promotion_subscriber")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionSubscriber {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;
    
    @Column(name = "name", length = 255)
    private String name;
    
    @Column(name = "active")
    private boolean active = true;
    
    @Column(name = "subscribed_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date subscribedAt;
    
    @Column(name = "unsubscribed_at")
    private Date unsubscribedAt;
}

