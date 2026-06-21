package tourAgency.tour_agency.service.booking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.List;
import java.util.UUID;

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
                .orElseThrow();

        Destination destination = destinationRepository.findById(bookingDto.getDestinationId())
                .orElseThrow();

        int persons;

        if (bookingDto.getPersons() != null) {
            persons = bookingDto.getPersons();
        } else {
            persons = 1;
        }

        BigDecimal totalPrice =
                destination.getPrice().multiply(BigDecimal.valueOf(persons));

        Booking booking = BookingMapper.toEntity(bookingDto, user, destination);

        booking.setPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);
    }

    public void updateStatus(UUID id, BookingStatus status) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow();

        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingMapper::toDto)
                .toList();
    }

    public BookingDto getById(UUID id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return BookingMapper.toDto(booking);
    }

    public BookingDto editBooking(UUID id, BookingEditDto dto) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingMapper.toEntity(dto, booking);

        bookingRepository.save(booking);

        return BookingMapper.toDto(booking);
    }
}