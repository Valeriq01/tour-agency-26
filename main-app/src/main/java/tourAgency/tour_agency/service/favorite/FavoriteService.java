package tourAgency.tour_agency.service.favorite;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tourAgency.tour_agency.exception.destination.DestinationNotFoundException;
import tourAgency.tour_agency.exception.user.UserNotFoundException;
import tourAgency.tour_agency.mapper.favorite.FavoriteMapper;
import tourAgency.tour_agency.model.dto.favorite.FavoriteDto;
import tourAgency.tour_agency.model.entity.destination.Destination;
import tourAgency.tour_agency.model.entity.favorite.Favorite;
import tourAgency.tour_agency.model.entity.user.User;
import tourAgency.tour_agency.repository.destination.DestinationRepository;
import tourAgency.tour_agency.repository.favorite.FavoriteRepository;
import tourAgency.tour_agency.repository.user.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           UserRepository userRepository,
                           DestinationRepository destinationRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.destinationRepository = destinationRepository;
    }

    public void addToFavorites(UUID userId, UUID destinationId) {

        if (favoriteRepository.existsByUserIdAndDestinationId(userId, destinationId)) {
            return;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                                "User with id [%s] does not exist.".formatted(userId)));

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new DestinationNotFoundException("Destination not found."));

        Favorite favorite = Favorite.builder()
                .user(user)
                .destination(destination)
                .build();

        favoriteRepository.save(favorite);
    }

    public List<FavoriteDto> getUserFavorites(UUID userId) {

        return FavoriteMapper.toDtoList(
                favoriteRepository.findAllByUserId(userId)
        );
    }

    public void removeFavorite(UUID userId, UUID destinationId) {

        favoriteRepository.findByUserIdAndDestinationId(userId, destinationId)
                .ifPresent(favoriteRepository::delete);
    }

    public Set<UUID> getFavoriteDestinationIds(UUID userId) {

        return favoriteRepository.findAllByUserId(userId)
                .stream()
                .map(favorite -> favorite.getDestination().getId())
                .collect(Collectors.toSet());
    }
}
