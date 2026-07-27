package tourAgency.tour_agency.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tourAgency.tour_agency.exception.booking.BookingAlreadyCompletedException;
import tourAgency.tour_agency.exception.booking.BookingNotFoundException;
import tourAgency.tour_agency.exception.destination.DestinationNotFoundException;
import tourAgency.tour_agency.exception.user.CannotChangeOwnRoleException;
import tourAgency.tour_agency.exception.user.EmailAlreadyExistsException;
import tourAgency.tour_agency.exception.user.UserNotFoundException;
import tourAgency.tour_agency.exception.user.UsernameAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex,
                                     Model model) {

        model.addAttribute("message",ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    public String handleDestinationNotFound(DestinationNotFoundException ex,
                                            Model model) {

        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public String handleBookingNotFound(BookingNotFoundException ex,
                                        Model model) {

        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public String handleUsernameAlreadyExists(UsernameAlreadyExistsException ex,
                                              Model model) {

        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExists(EmailAlreadyExistsException ex,
                                           Model model) {

        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(CannotChangeOwnRoleException.class)
    public String handleCannotChangeOwnRole(CannotChangeOwnRoleException ex,
                                            Model model) {

        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(BookingAlreadyCompletedException.class)
    public String handleBookingAlreadyCompleted(BookingAlreadyCompletedException ex,
                                                Model model) {

        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex,
                                        Model model) {

        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}