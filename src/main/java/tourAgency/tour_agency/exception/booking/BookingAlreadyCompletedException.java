package tourAgency.tour_agency.exception.booking;

public class BookingAlreadyCompletedException extends RuntimeException {

    public BookingAlreadyCompletedException(String message) {
        super(message);
    }
}