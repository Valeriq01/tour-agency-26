package tourAgency.tour_agency.model.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tourAgency.tour_agency.model.entity.booking.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank
    private String password;
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

}
