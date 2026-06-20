package tourAgency.tour_agency.mapper;

import lombok.NoArgsConstructor;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.model.entity.user.User;

@NoArgsConstructor
public class UserMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isActive(user.isActive())
                .role(user.getRole())
                .build();
    }
}