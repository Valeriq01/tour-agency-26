package tourAgency.tour_agency.repository.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourAgency.tour_agency.model.entity.favorite.Favorite;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    List<Favorite> findAllByUserId(UUID userId);
    Optional<Favorite> findByUserIdAndDestinationId(UUID userId, UUID destinationId);
    boolean existsByUserIdAndDestinationId(UUID userId, UUID destinationId);
}