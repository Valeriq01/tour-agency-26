package tourAgency.tour_agency.model.entity.booking;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tourAgency.tour_agency.model.entity.destination.Destination;
import tourAgency.tour_agency.model.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;
    private LocalDate startDate;
    private LocalDate endDate;
    @Positive
    private Integer persons;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @Positive
    private BigDecimal price;
    private String notes;
}
