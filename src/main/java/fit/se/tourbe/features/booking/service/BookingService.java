package fit.se.tourbe.features.booking.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import fit.se.tourbe.features.booking.dto.BookingServiceDTO;
import fit.se.tourbe.features.tour.dto.TourDTO;
import fit.se.tourbe.features.booking.entities.Booking;
import fit.se.tourbe.features.booking.repositories.BookingRepository;

public interface BookingService {
	void add(BookingServiceDTO bookingservicedto);
	void update(BookingServiceDTO bookingservicedto);
	void delete(Integer id);
	List<BookingServiceDTO> getAll();
	BookingServiceDTO getOne(Integer id);
	 TourDTO getTourDetails(int tourId);
	
}



