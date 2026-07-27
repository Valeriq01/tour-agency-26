package tourAgency.tour_agency.model.dto.favorite;

import lombok.Builder;
import lombok.Data;
import tourAgency.tour_agency.model.dto.destination.DestinationDto;

import java.util.UUID;

@Data
@Builder
public class FavoriteDto {

    private UUID id;
    private DestinationDto destination;
}