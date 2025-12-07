package fit.se.tourbe.features.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fit.se.tourbe.features.booking.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	

}
