package tourAgency.tour_agency.mapper.destination;

import lombok.NoArgsConstructor;
import tourAgency.tour_agency.model.dto.destination.DestinationDto;
import tourAgency.tour_agency.model.entity.destination.Destination;

import java.util.List;

@NoArgsConstructor
public class DestinationMapper {

    public static DestinationDto toDto(Destination destination) {
        if (destination == null) {
            return null;
        }

        return DestinationDto.builder()
                .id(destination.getId())
                .name(destination.getName())
                .country(destination.getCountry())
                .description(destination.getDescription())
                .price(destination.getPrice())
                .days(destination.getDays())
                .availableSpots(destination.getAvailableSpots())
                .imageUrl(destination.getImageUrl())
                .experiences(destination.getExperiences())
                .build();
    }

    public static List<DestinationDto> toDtoList(List<Destination> list) {
        return list.stream()
                .map(DestinationMapper::toDto)
                .toList();
    }
}
