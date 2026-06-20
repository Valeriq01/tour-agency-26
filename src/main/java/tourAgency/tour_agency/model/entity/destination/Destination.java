package tourAgency.tour_agency.model.entity.destination;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tourAgency.tour_agency.model.entity.booking.Booking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "destinations")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String country;
    private String description;
    @Positive
    private BigDecimal price;
    @NotNull
    @Positive
    private int days;
    @Positive
    private Integer availableSpots;
    private String imageUrl;
    private String experiences;
    @OneToMany(mappedBy = "destination")
    private List<Booking> bookings = new ArrayList<>();
}