package tourAgency.tour_agency.service.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tourAgency.tour_agency.mapper.UserMapper;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.model.dto.user.UserLoginRequest;
import tourAgency.tour_agency.model.dto.user.UserRegisterRequest;
import tourAgency.tour_agency.model.entity.user.User;
import tourAgency.tour_agency.repository.user.UserRepository;

import java.util.Optional;

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

    public UserDto login(UserLoginRequest userLoginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(userLoginRequest.getUsername());

        if (optionalUser.isEmpty() ||
                !passwordEncoder.matches(userLoginRequest.getPassword(), optionalUser.get().getPassword())
        ) {

            throw new RuntimeException("Username or password mismatch!");
        }

        return UserMapper.toUserDto(optionalUser.get());
    }

    public UserDto register(UserRegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = UserMapper.toUserEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return UserMapper.toUserDto(userRepository.save(user));
    }
}
