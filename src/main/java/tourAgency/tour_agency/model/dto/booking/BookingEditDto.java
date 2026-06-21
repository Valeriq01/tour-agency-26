package tourAgency.tour_agency.model.dto.booking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class BookingEditDto {

    @NotNull
    private UUID id;

    @NotNull(message = "Persons are required")
    @Min(value = 1, message = "Minimum 1 person")
    private Integer persons;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private String notes;
}