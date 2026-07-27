package tourAgency.tour_agency.web.admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tourAgency.tour_agency.exception.user.CannotChangeOwnRoleException;
import tourAgency.tour_agency.model.entity.user.UserRole;
import tourAgency.tour_agency.security.ApplicationUserDetails;
import tourAgency.tour_agency.service.user.UserService;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView users() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin-users");
        modelAndView.addObject("users", userService.getAllUsers());

        return modelAndView;
    }

    @PostMapping("/users/{id}/role")
    public String changeRole(@PathVariable UUID id,
                             @RequestParam UserRole role,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {

        UUID currentUserId = getCurrentUserId(authentication);

        try {
            userService.changeRole(currentUserId, id, role);
        } catch (CannotChangeOwnRoleException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/users";
    }

    private UUID getCurrentUserId(Authentication authentication) {

        ApplicationUserDetails userDetails =
                (ApplicationUserDetails) authentication.getPrincipal();

        return userDetails.getUser().getId();
    }


}