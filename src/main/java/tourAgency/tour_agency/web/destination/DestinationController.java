package tourAgency.tour_agency.web.destination;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import tourAgency.tour_agency.model.dto.destination.DestinationDto;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.service.destination.DestinationService;
import tourAgency.tour_agency.service.user.UserService;

import java.util.List;
import java.util.UUID;

@Controller
public class DestinationController {

    private final DestinationService destinationService;
    private final UserService userService;

    public DestinationController(DestinationService destinationService, UserService userService) {
        this.destinationService = destinationService;
        this.userService = userService;
    }

    @GetMapping("/destinations")
    public ModelAndView getDestinations(HttpSession session) {
        List<DestinationDto> destinationDtoList = destinationService.getAll();

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        UserDto user = userService.getById(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("destinations");
        modelAndView.addObject("user", user);
        modelAndView.addObject("destinations", destinationDtoList);

        return modelAndView;
    }

    @GetMapping("/destinations/{id}")
    public ModelAndView getDestinationDetails(@PathVariable UUID id, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        DestinationDto destination = destinationService.getById(id);
        UserDto user = userService.getById(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("destination-details");
        modelAndView.addObject("destination", destination);
        modelAndView.addObject("user", user);

        return modelAndView;
    }


    @GetMapping("/destinations/details/{id}")
    public ModelAndView destinationDetails(@PathVariable UUID id, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        DestinationDto destination = destinationService.getById(id);
        UserDto user = userService.getById(userId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("destination-details");
        modelAndView.addObject("destination", destination);
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}