package tourAgency.tour_agency.service.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tourAgency.tour_agency.exception.booking.BookingAlreadyCompletedException;
import tourAgency.tour_agency.exception.booking.BookingNotFoundException;
import tourAgency.tour_agency.exception.destination.DestinationNotFoundException;
import tourAgency.tour_agency.exception.user.UserNotFoundException;
import tourAgency.tour_agency.mapper.booking.BookingMapper;
import tourAgency.tour_agency.model.dto.booking.BookingDto;
import tourAgency.tour_agency.model.dto.booking.BookingEditDto;
import tourAgency.tour_agency.model.dto.booking.BookingRequestDto;
import tourAgency.tour_agency.model.entity.booking.Booking;
import tourAgency.tour_agency.model.entity.booking.BookingStatus;
import tourAgency.tour_agency.model.entity.destination.Destination;
import tourAgency.tour_agency.model.entity.user.User;
import tourAgency.tour_agency.repository.booking.BookingRepository;
import tourAgency.tour_agency.repository.destination.DestinationRepository;
import tourAgency.tour_agency.repository.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, DestinationRepository destinationRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.destinationRepository = destinationRepository;
    }

    public List<BookingDto> getByUserId(UUID userId) {
        return bookingRepository.findAllByUserId(userId)
                .stream()
                .map(BookingMapper::toDto)
                .toList();
    }

    public void createBooking(BookingRequestDto bookingDto, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        Destination destination = destinationRepository.findById(bookingDto.getDestinationId())
                .orElseThrow(() -> new DestinationNotFoundException("Destination not found."));

        BigDecimal totalPrice = calculateTotalPrice(
                        bookingDto.getDestinationId(),
                        bookingDto.getPersons());

        Booking booking = BookingMapper.toEntity(bookingDto, user, destination);

        booking.setPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);

        log.info("Booking {} created by user {} for destination {}",
                booking.getId(),
                userId,
                destination.getName());
    }

    public BigDecimal calculateTotalPrice(UUID destinationId, Integer persons) {

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new DestinationNotFoundException("Destination not found."));

        int totalPersons = persons != null ? persons : 1;

        return destination.getPrice()
                .multiply(BigDecimal.valueOf(totalPersons));
    }

    public void updateStatus(UUID id, BookingStatus status) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new BookingAlreadyCompletedException("Completed bookings cannot be modified.");
        }

        booking.setStatus(status);

        bookingRepository.save(booking);

        log.info("Booking {} status changed to {}", id, status);
    }

    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingMapper::toDto)
                .toList();
    }

    public BookingDto getById(UUID id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        return BookingMapper.toDto(booking);
    }

    public BookingDto editBooking(UUID id, BookingEditDto dto) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        BookingMapper.toEntity(dto, booking);

        bookingRepository.save(booking);

        log.info("Booking {} updated successfully", booking.getId());

        return BookingMapper.toDto(booking);
    }

    public void completeFinishedBookings() {

        List<Booking> bookings =
                bookingRepository.findAllByStatusAndEndDateBefore(
                        BookingStatus.CONFIRMED,
                        LocalDate.now());

        for (Booking booking : bookings) {
            booking.setStatus(BookingStatus.COMPLETED);
        }

        log.info("{} bookings marked as COMPLETED.", bookings.size());
    }

    public void deleteOldCancelledBookings() {

        LocalDate thresholdDate = LocalDate.now().minusDays(30);

        long deletedCount = bookingRepository.countByStatusAndEndDateBefore(
                BookingStatus.CANCELLED,
                thresholdDate);

        bookingRepository.deleteAllByStatusAndEndDateBefore(
                BookingStatus.CANCELLED,
                thresholdDate);

        log.info("Deleted {} old cancelled bookings.", deletedCount);
    }
}