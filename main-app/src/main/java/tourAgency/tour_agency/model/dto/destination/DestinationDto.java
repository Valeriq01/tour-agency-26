package tourAgency.tour_agency.model.dto.destination;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class DestinationDto {

    private UUID id;
    private String name;
    private String country;
    private String description;
    private BigDecimal price;
    private int days;
    private Integer availableSpots;
    private String imageUrl;
    private String experiences;
}
