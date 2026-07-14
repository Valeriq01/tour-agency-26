package tourAgency.tour_agency.service.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tourAgency.tour_agency.exception.user.CannotChangeOwnRoleException;
import tourAgency.tour_agency.exception.user.EmailAlreadyExistsException;
import tourAgency.tour_agency.exception.user.UserNotFoundException;
import tourAgency.tour_agency.exception.user.UsernameAlreadyExistsException;
import tourAgency.tour_agency.mapper.user.UserMapper;
import tourAgency.tour_agency.model.dto.user.EditUserRequest;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.model.dto.user.UserRegisterRequest;
import tourAgency.tour_agency.model.entity.user.User;
import tourAgency.tour_agency.model.entity.user.UserRole;
import tourAgency.tour_agency.repository.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto register(UserRegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        User user = UserMapper.toUserEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return UserMapper.toUserDto(userRepository.save(user));
    }

    public UserDto getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User with id [%s] does not exist.".formatted(id)));
        return UserMapper.toUserDto(user);
    }

    public UserDto update(String id, EditUserRequest editUserRequest) {
        User entity = userRepository.findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new UserNotFoundException("User with id [%s] does not exist.".formatted(id)));

        entity.setUsername(editUserRequest.getUsername());
        entity.setFirstName(editUserRequest.getFirstName());
        entity.setLastName(editUserRequest.getLastName());
        entity.setEmail(editUserRequest.getEmail());

        User updatedUser = userRepository.save(entity);

        return UserMapper.toUserDto(updatedUser);
    }

    public List<UserDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public void changeRole(UUID currentUserId,
                           UUID targetUserId,
                           UserRole role) {

        if (currentUserId.equals(targetUserId)) {
            throw new CannotChangeOwnRoleException("You cannot change your own role.");
        }

        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id [%s] does not exist.".formatted(targetUserId)));

        user.setRole(role);

        userRepository.save(user);
    }
}
