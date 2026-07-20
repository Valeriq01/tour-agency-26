package tourAgency.tour_agency.mapper.favorite;

import lombok.NoArgsConstructor;
import tourAgency.tour_agency.mapper.destination.DestinationMapper;
import tourAgency.tour_agency.model.dto.favorite.FavoriteDto;
import tourAgency.tour_agency.model.entity.favorite.Favorite;

import java.util.List;

@NoArgsConstructor
public class FavoriteMapper {

    public static FavoriteDto toDto(Favorite favorite) {

        if (favorite == null) {
            return null;
        }

        return FavoriteDto.builder()
                .id(favorite.getId())
                .destination(DestinationMapper.toDto(favorite.getDestination()))
                .build();
    }

    public static List<FavoriteDto> toDtoList(List<Favorite> favorites) {
        return favorites.stream()
                .map(FavoriteMapper::toDto)
                .toList();
    }
}