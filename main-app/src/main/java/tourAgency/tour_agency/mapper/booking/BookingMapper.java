package tourAgency.tour_agency.mapper.booking;

import lombok.NoArgsConstructor;
import tourAgency.tour_agency.model.dto.booking.BookingDto;
import tourAgency.tour_agency.model.dto.booking.BookingEditDto;
import tourAgency.tour_agency.model.dto.booking.BookingRequestDto;
import tourAgency.tour_agency.model.entity.booking.Booking;
import tourAgency.tour_agency.model.entity.booking.BookingStatus;
import tourAgency.tour_agency.model.entity.destination.Destination;
import tourAgency.tour_agency.model.entity.user.User;

import java.math.BigDecimal;

@NoArgsConstructor
public class BookingMapper {

    public static BookingDto toDto(Booking booking) {

        if (booking == null) {
            return null;
        }

        return BookingDto.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .destinationId(booking.getDestination().getId())
                .destinationName(booking.getDestination().getName())
                .country(booking.getDestination().getCountry())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .persons(booking.getPersons())
                .status(booking.getStatus())
                .price(booking.getPrice())
                .notes(booking.getNotes())
                .build();
    }

    public static Booking toEntity(BookingRequestDto dto, User user, Destination destination) {

        if (dto == null) {
            return null;
        }

        int persons = dto.getPersons() != null ? dto.getPersons() : 1;

        BigDecimal price = destination.getPrice().multiply(BigDecimal.valueOf(persons));

        return Booking.builder()
                .user(user)
                .destination(destination)
                .persons(persons)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(BookingStatus.PENDING)
                .price(price)
                .notes(dto.getNotes())
                .build();
    }

    public static Booking toEntity(BookingEditDto dto, Booking booking) {

        if (dto == null || booking == null) {
            return booking;
        }

        booking.setPersons(dto.getPersons());
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setNotes(dto.getNotes());

        BigDecimal price = booking.getDestination()
                .getPrice()
                .multiply(BigDecimal.valueOf(dto.getPersons()));

        booking.setPrice(price);

        return booking;
    }

    public static BookingEditDto toEditDto(BookingDto booking) {

        if (booking == null) {
            return null;
        }

        return BookingEditDto.builder()
                .id(booking.getId())
                .persons(booking.getPersons())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .notes(booking.getNotes())
                .build();
    }
}