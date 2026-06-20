package tourAgency.tour_agency.model.dto.booking;

import lombok.Builder;
import lombok.Data;
import tourAgency.tour_agency.model.entity.booking.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class BookingDto {

    private UUID id;
    private UUID userId;
    private UUID destinationId;
    private String destinationName;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer persons;
    private BookingStatus status;
    private BigDecimal price;
    private String notes;
}
