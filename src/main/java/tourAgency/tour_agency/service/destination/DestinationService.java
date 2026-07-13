package tourAgency.tour_agency.service.destination;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tourAgency.tour_agency.exception.destination.DestinationNotFoundException;
import tourAgency.tour_agency.mapper.destination.DestinationMapper;
import tourAgency.tour_agency.model.dto.destination.DestinationDto;
import tourAgency.tour_agency.model.entity.destination.Destination;
import tourAgency.tour_agency.repository.destination.DestinationRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<DestinationDto> getAll() {
        List<Destination> allDestinationsByOrderByNameAsc = destinationRepository.findAllByOrderByNameAsc();
        return DestinationMapper.toDtoList(allDestinationsByOrderByNameAsc);
    }

    public DestinationDto getById(UUID id) {
        return DestinationMapper.toDto(
                destinationRepository.findById(id)
                        .orElseThrow(() -> new DestinationNotFoundException("Destination not found"))
        );
    }

}