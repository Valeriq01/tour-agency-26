package tourAgency.tour_agency.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import tourAgency.tour_agency.model.dto.destination.DestinationDto;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.security.ApplicationUserDetails;
import tourAgency.tour_agency.service.destination.DestinationService;
import tourAgency.tour_agency.service.user.UserService;

import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    private final UserService userService;
    private final DestinationService destinationService;

    public IndexController(UserService userService,
                           DestinationService destinationService) {
        this.userService = userService;
        this.destinationService = destinationService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(Authentication authentication) {

        ApplicationUserDetails userDetails =
                (ApplicationUserDetails) authentication.getPrincipal();

        UUID userId = userDetails.getUser().getId();

        UserDto user = userService.getById(userId);

        List<DestinationDto> popularDestinations =
                destinationService.getAll()
                        .stream()
                        .limit(3)
                        .toList();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("user", user);
        modelAndView.addObject("popularDestinations", popularDestinations);

        return modelAndView;
    }
}