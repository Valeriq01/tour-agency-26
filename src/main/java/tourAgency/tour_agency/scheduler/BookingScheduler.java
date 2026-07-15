package tourAgency.tour_agency.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tourAgency.tour_agency.service.booking.BookingService;

@Slf4j
@Component
public class BookingScheduler {

    private final BookingService bookingService;

    public BookingScheduler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void completeFinishedBookings() {

        bookingService.completeFinishedBookings();

    }

    @Scheduled(fixedRate = 86400000)
    public void deleteOldCancelledBookings() {

        bookingService.deleteOldCancelledBookings();
    }
}