package tourAgency.tour_agency.web.user;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tourAgency.tour_agency.mapper.user.UserMapper;
import tourAgency.tour_agency.model.dto.booking.BookingDto;
import tourAgency.tour_agency.model.dto.user.EditUserRequest;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.model.dto.user.UserLoginRequest;
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
        UserLoginRequest userLoginRequest = UserLoginRequest.builder().build();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("userLoginRequest", userLoginRequest);

        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid UserLoginRequest userLoginRequest,
                              BindingResult bindingResult,
                              HttpSession httpSession,
                              HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            return modelAndView;
        }

        UserDto user = userService.login(userLoginRequest);
        httpSession.setAttribute("user_id", user.getId());

        return new ModelAndView("redirect:/home");
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
    public ModelAndView registerUser(@Valid UserRegisterRequest userRegisterRequest,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView();

        try {
            userService.register(userRegisterRequest);
            return new ModelAndView("redirect:/login");

        } catch (RuntimeException ex) {
            modelAndView.setViewName("register");
            modelAndView.addObject("error", ex.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profile(@PathVariable String id) {

        UserDto user = userService.getById(UUID.fromString(id));
        ModelAndView modelAndView = new ModelAndView();
        List<BookingDto> bookings = bookingService.getByUserId(UUID.fromString(id));

        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("bookings", bookings);

        return modelAndView;
    }

    @GetMapping("/profile/edit-profile/{id}")
    public ModelAndView editProfile(@PathVariable String id) {

        UUID userId = UUID.fromString(id);

        UserDto user = userService.getById(userId);

        if (user == null) {
            return new ModelAndView("redirect:/home?error=user-not-found");
        }

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
            ModelAndView modelAndView = new ModelAndView("edit-profile");

            UserDto user = userService.getById(UUID.fromString(id));
            modelAndView.addObject("user", user);

            return modelAndView;
        }

        userService.update(id, editUserRequest);

        return new ModelAndView("redirect:/profile/" + id);
    }
}