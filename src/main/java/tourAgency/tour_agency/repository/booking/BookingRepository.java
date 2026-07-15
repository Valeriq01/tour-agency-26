package tourAgency.tour_agency.repository.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourAgency.tour_agency.model.entity.booking.Booking;
import tourAgency.tour_agency.model.entity.booking.BookingStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findAllByUserId(UUID userId);
    List<Booking> findAllByStatusAndEndDateBefore(BookingStatus status, LocalDate endDate);
    void deleteAllByStatusAndEndDateBefore(BookingStatus status, LocalDate date);
    long countByStatusAndEndDateBefore(BookingStatus status, LocalDate date);
}
