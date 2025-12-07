package fit.se.tourbe.features.booking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.se.tourbe.features.booking.dto.BookingServiceDTO;
import fit.se.tourbe.features.booking.dto.TourDTO;
import fit.se.tourbe.features.booking.entities.Booking;
import fit.se.tourbe.features.booking.repositories.BookingRepository;
import fit.se.tourbe.features.booking.service.BookingService;
import fit.se.tourbe.features.tour.service.TourService;

@Service
class BookingServiceImpl implements BookingService{
	@Autowired
	BookingRepository bookingrepository;	

	@Autowired
	ModelMapper modelMapper;
	
	 @Autowired
	 private TourService tourService;

	    public TourDTO getTourDetails(int tourId) {
	        return tourService.getOne(tourId);
	    }

	@Override
	public void add(BookingServiceDTO bookingservicedto) {
		// TODO Auto-generated method stub
		Booking booking = modelMapper.map(bookingservicedto, Booking.class);

		bookingrepository.save(booking);

		bookingservicedto.setId(booking.getId());
	}
	@Override
	public void update(BookingServiceDTO bookingservicedto) {
		// TODO Auto-generated method stub
		Booking booking = bookingrepository.findById(bookingservicedto.getId()).orElse(null);
		if (booking != null) {
			modelMapper.map(bookingservicedto, booking);
			bookingrepository.save(booking);
		}
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Booking booking = bookingrepository.findById(id).orElse(null);
		if (booking != null) {
			bookingrepository.delete(booking);
		}

	}

	@Override
	public List<BookingServiceDTO> getAll() {
		// TODO Auto-generated method stub
		List<BookingServiceDTO> bookingServiceDTOs = new ArrayList<>();

		bookingrepository.findAll().forEach((booking) -> {
			bookingServiceDTOs.add(modelMapper.map(booking, BookingServiceDTO.class));
		});
		return bookingServiceDTOs;
	}
	@Override
	public BookingServiceDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		Booking booking = bookingrepository.findById(id).orElse(null);
		if (booking != null) {
            return modelMapper.map(booking, BookingServiceDTO.class);
		}
		return null;
	}

}