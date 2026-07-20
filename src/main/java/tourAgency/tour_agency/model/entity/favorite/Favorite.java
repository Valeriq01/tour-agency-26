package tourAgency.tour_agency.model.entity.favorite;

import jakarta.persistence.*;
import lombok.*;
import tourAgency.tour_agency.model.entity.destination.Destination;
import tourAgency.tour_agency.model.entity.user.User;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favorites",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "destination_id"})
        }
)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;
}
