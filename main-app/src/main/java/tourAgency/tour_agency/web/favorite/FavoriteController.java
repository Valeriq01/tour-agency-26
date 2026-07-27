package tourAgency.tour_agency.web.favorite;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.security.ApplicationUserDetails;
import tourAgency.tour_agency.service.favorite.FavoriteService;
import tourAgency.tour_agency.service.user.UserService;

import java.util.UUID;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService,
                              UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @PostMapping("/add/{destinationId}")
    public String addFavorite(@PathVariable UUID destinationId,
                              @RequestParam String redirectUrl,
                              Authentication authentication) {

        UUID userId = getCurrentUserId(authentication);

        favoriteService.addToFavorites(userId, destinationId);

        return "redirect:" + redirectUrl;
    }

    @PostMapping("/remove/{destinationId}")
    public String removeFavorite(@PathVariable UUID destinationId,
                                 @RequestParam String redirectUrl,
                                 Authentication authentication) {

        UUID userId = getCurrentUserId(authentication);

        favoriteService.removeFavorite(userId, destinationId);

        return "redirect:" + redirectUrl;
    }

    @GetMapping
    public ModelAndView myFavorites(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        UUID userId = getCurrentUserId(authentication);

        UserDto user = userService.getById(userId);

        modelAndView.setViewName("my-favorites");
        modelAndView.addObject("user", user);
        modelAndView.addObject("favorites",
                favoriteService.getUserFavorites(userId));

        return modelAndView;
    }

    private UUID getCurrentUserId(Authentication authentication) {
        ApplicationUserDetails userDetails =
                (ApplicationUserDetails) authentication.getPrincipal();

        return userDetails.getUser().getId();
    }
}