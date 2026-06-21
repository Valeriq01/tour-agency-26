package tourAgency.tour_agency.model.dto.booking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class BookingRequestDto {

    @NotNull(message = "Destination is required")
    private UUID destinationId;

    @NotNull(message = "At least 1 person required")
    @Min(value = 1, message = "At least 1 person required")
    private Integer persons;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Size(max = 200, message = "Notes too long")
    private String notes;
}
