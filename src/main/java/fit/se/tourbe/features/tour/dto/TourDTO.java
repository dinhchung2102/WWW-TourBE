package fit.se.tourbe.features.tour.dto;

import java.util.Date;

import fit.se.tourbe.features.promotion.dto.PromotionDTO;
import lombok.Data;

@Data
public class TourDTO {

	private int id_tour;

	private String title;

	private String description;

	private String location;

	private int duration;

	private double price;

	private int max_participants;

	private Date start_date;

	private Date end_date;

	private Date created_at;
	
	private String image;
	
	private Integer promotionId; // ID of promotion (can be null) - used for create/update
	private PromotionDTO promotion; // Full promotion information (used for response)
}
