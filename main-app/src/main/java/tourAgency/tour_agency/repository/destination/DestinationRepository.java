package tourAgency.tour_agency.repository.destination;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourAgency.tour_agency.model.entity.destination.Destination;

import java.util.List;
import java.util.UUID;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, UUID> {
    List<Destination> findAllByOrderByNameAsc();
}