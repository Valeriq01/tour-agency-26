package tourAgency.tour_agency.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import tourAgency.tour_agency.model.dto.destination.DestinationDto;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.service.destination.DestinationService;
import tourAgency.tour_agency.service.user.UserService;

import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    private final UserService userService;
    private final DestinationService destinationService;

    public IndexController(UserService userService, DestinationService destinationService) {
        this.userService = userService;
        this.destinationService = destinationService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        UserDto user = userService.getById(userId);

        List<DestinationDto> popularDestinations =
                destinationService.getAll()
                        .stream()
                        .limit(3)
                        .toList();

        return new ModelAndView("home")
                .addObject("user", user)
                .addObject("popularDestinations", popularDestinations);
    }

    @GetMapping("/logout")
    public ModelAndView getLogoutPage(HttpSession httpSession) {
        httpSession.invalidate();
        return new ModelAndView("redirect:/");
    }
}

