package tourAgency.tour_agency.web.destination;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import tourAgency.tour_agency.model.dto.destination.DestinationDto;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.security.ApplicationUserDetails;
import tourAgency.tour_agency.service.destination.DestinationService;
import tourAgency.tour_agency.service.user.UserService;

import java.util.List;
import java.util.UUID;

@Controller
public class DestinationController {

    private final DestinationService destinationService;
    private final UserService userService;

    public DestinationController(DestinationService destinationService,
                                 UserService userService) {
        this.destinationService = destinationService;
        this.userService = userService;
    }

    @GetMapping("/destinations")
    public ModelAndView getDestinations(Authentication authentication) {

        ApplicationUserDetails userDetails =
                (ApplicationUserDetails) authentication.getPrincipal();

        UUID userId = userDetails.getUser().getId();

        UserDto user = userService.getById(userId);
        List<DestinationDto> destinationDtoList = destinationService.getAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("destinations");
        modelAndView.addObject("user", user);
        modelAndView.addObject("destinations", destinationDtoList);

        return modelAndView;
    }

    @GetMapping("/destinations/{id}")
    public ModelAndView getDestinationDetails(@PathVariable UUID id,
                                              Authentication authentication) {

        ApplicationUserDetails userDetails =
                (ApplicationUserDetails) authentication.getPrincipal();

        UUID userId = userDetails.getUser().getId();

        UserDto user = userService.getById(userId);
        DestinationDto destination = destinationService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("destination-details");
        modelAndView.addObject("user", user);
        modelAndView.addObject("destination", destination);

        return modelAndView;
    }

    @GetMapping("/destinations/details/{id}")
    public ModelAndView destinationDetails(@PathVariable UUID id,
                                           Authentication authentication) {

        ApplicationUserDetails userDetails =
                (ApplicationUserDetails) authentication.getPrincipal();

        UUID userId = userDetails.getUser().getId();

        UserDto user = userService.getById(userId);
        DestinationDto destination = destinationService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("destination-details");
        modelAndView.addObject("user", user);
        modelAndView.addObject("destination", destination);

        return modelAndView;
    }
}