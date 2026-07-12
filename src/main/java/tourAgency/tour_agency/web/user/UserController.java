package tourAgency.tour_agency.web.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tourAgency.tour_agency.mapper.user.UserMapper;
import tourAgency.tour_agency.model.dto.booking.BookingDto;
import tourAgency.tour_agency.model.dto.user.EditUserRequest;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.model.dto.user.UserRegisterRequest;
import tourAgency.tour_agency.service.booking.BookingService;
import tourAgency.tour_agency.service.user.UserService;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;

    public UserController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {

        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder().build();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("userRegisterRequest", userRegisterRequest);

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid @ModelAttribute UserRegisterRequest userRegisterRequest,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register");
            modelAndView.addObject("userRegisterRequest", userRegisterRequest);

            return modelAndView;
        }

        try {

            userService.register(userRegisterRequest);

            return new ModelAndView("redirect:/login");

        } catch (RuntimeException ex) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register");
            modelAndView.addObject("userRegisterRequest", userRegisterRequest);
            modelAndView.addObject("error", ex.getMessage());

            return modelAndView;
        }
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profile(@PathVariable UUID id) {

        UserDto user = userService.getById(id);
        List<BookingDto> bookings = bookingService.getByUserId(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("bookings", bookings);

        return modelAndView;
    }

    @GetMapping("/profile/edit-profile/{id}")
    public ModelAndView editProfile(@PathVariable UUID id) {

        UserDto user = userService.getById(id);
        EditUserRequest editUserRequest = UserMapper.toEditUserRequest(user);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit-profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("editUserRequest", editUserRequest);

        return modelAndView;
    }

    @PutMapping("/profile/{id}")
    public ModelAndView updateProfile(@PathVariable String id,
                                      @Valid @ModelAttribute EditUserRequest editUserRequest,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("edit-profile");

            UserDto user = userService.getById(UUID.fromString(id));

            modelAndView.addObject("user", user);
            modelAndView.addObject("editUserRequest", editUserRequest);

            return modelAndView;
        }

        userService.update(id, editUserRequest);

        return new ModelAndView("redirect:/profile/" + id);
    }
}