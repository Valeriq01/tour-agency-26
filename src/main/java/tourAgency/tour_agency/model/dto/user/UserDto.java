package tourAgency.tour_agency.model.dto.user;

import lombok.Builder;
import lombok.Data;
import tourAgency.tour_agency.model.dto.booking.BookingDto;
import tourAgency.tour_agency.model.entity.user.UserRole;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDto {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isActive;
    private UserRole role;
    private List<BookingDto> bookings;
}