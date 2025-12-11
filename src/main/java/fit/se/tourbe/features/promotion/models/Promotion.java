package fit.se.tourbe.features.promotion.models;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "title", length = 255, nullable = false)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "discount_percent")
    private double discountPercent; // Phần trăm giảm giá
    
    @Column(name = "discount_amount")
    private Double discountAmount; // Số tiền giảm cố định (nếu có)
    
    @Column(name = "code", length = 50, unique = true)
    private String code; // Mã khuyến mãi
    
    @Column(name = "start_date")
    private Date startDate;
    
    @Column(name = "end_date")
    private Date endDate;
    
    @Column(name = "min_order_amount")
    private Double minOrderAmount; // Số tiền tối thiểu để áp dụng
    
    @Column(name = "max_discount_amount")
    private Double maxDiscountAmount; // Số tiền giảm tối đa
    
    @Column(name = "usage_limit")
    private Integer usageLimit; // Giới hạn số lần sử dụng
    
    @Column(name = "used_count")
    private int usedCount = 0; // Số lần đã sử dụng
    
    @Column(name = "active")
    private boolean active = true;
    
    @Column(name = "image", length = 500)
    private String image;
    
    @Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;
    
    @Column(name = "updated_at")
    private Date updatedAt;
}

