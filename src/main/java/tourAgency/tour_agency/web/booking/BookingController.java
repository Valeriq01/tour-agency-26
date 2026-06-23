package tourAgency.tour_agency.web.booking;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tourAgency.tour_agency.mapper.booking.BookingMapper;
import tourAgency.tour_agency.model.dto.booking.BookingDto;
import tourAgency.tour_agency.model.dto.booking.BookingEditDto;
import tourAgency.tour_agency.model.dto.booking.BookingRequestDto;
import tourAgency.tour_agency.model.dto.destination.DestinationDto;
import tourAgency.tour_agency.model.dto.user.UserDto;
import tourAgency.tour_agency.model.entity.booking.BookingStatus;
import tourAgency.tour_agency.service.booking.BookingService;
import tourAgency.tour_agency.service.destination.DestinationService;
import tourAgency.tour_agency.service.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class BookingController {

    private final DestinationService destinationService;
    private final UserService userService;
    private final BookingService bookingService;

    public BookingController(DestinationService destinationService, UserService userService, BookingService bookingService) {
        this.destinationService = destinationService;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping("/booking/{id}")
    public ModelAndView getBookingPage(@PathVariable UUID id, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        UserDto user = userService.getById(userId);
        DestinationDto destination = destinationService.getById(id);

        modelAndView.setViewName("booking");
        modelAndView.addObject("user", user);
        modelAndView.addObject("destination", destination);

        BookingRequestDto bookingRequestDto = BookingRequestDto.builder().build();
        bookingRequestDto.setDestinationId(id);

        modelAndView.addObject("bookingRequestDto", bookingRequestDto);

        return modelAndView;
    }

    @PostMapping("/booking/save")
    public ModelAndView saveBooking(@Valid @ModelAttribute BookingRequestDto bookingDto,
                                    BindingResult bindingResult,
                                    HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        if (bookingDto.getStartDate() != null &&
                bookingDto.getEndDate() != null) {

            if (bookingDto.getStartDate().isBefore(LocalDate.now())) {
                bindingResult.rejectValue(
                        "startDate",
                        "date.invalid",
                        "Start date cannot be before today"
                );
            }

            if (bookingDto.getEndDate().isBefore(bookingDto.getStartDate())) {
                bindingResult.rejectValue(
                        "endDate",
                        "date.invalid",
                        "End date cannot be before start date"
                );
            }
        }

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName("booking");

            modelAndView.addObject("user", userService.getById(userId));

            if (bookingDto.getDestinationId() != null) {
                modelAndView.addObject("destination", destinationService.getById(bookingDto.getDestinationId()));
            }

            modelAndView.addObject("bookingRequestDto", bookingDto);

            return modelAndView;
        }

        bookingService.createBooking(bookingDto, userId);

        modelAndView.setViewName("redirect:/my-bookings");
        return modelAndView;
    }

    @GetMapping("/my-bookings")
    public ModelAndView myBookings(HttpSession session) {

        ModelAndView model = new ModelAndView();

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            model.setViewName("redirect:/login");
            return model;
        }

        UserDto user = userService.getById(userId);

        List<BookingDto> bookings;

        if (user.getRole().name().equals("ADMIN")) {
            bookings = bookingService.getAllBookings();
        } else {
            bookings = bookingService.getByUserId(userId);
        }

        model.setViewName("my-bookings");
        model.addObject("user", user);
        model.addObject("bookings", bookings);

        return model;
    }

    @DeleteMapping("/booking/{id}")
    public ModelAndView cancel(@PathVariable UUID id, HttpSession session) {

        ModelAndView model = new ModelAndView();

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            model.setViewName("redirect:/login");
            return model;
        }

        UserDto user = userService.getById(userId);

        if (!user.getRole().name().equals("ADMIN")) {
            model.setViewName("redirect:/my-bookings?error=no-permission");
            return model;
        }

        bookingService.updateStatus(id, BookingStatus.CANCELLED);

        model.setViewName("redirect:/my-bookings");
        return model;
    }

    @PostMapping("/booking/approve/{id}")
    public ModelAndView approve(@PathVariable UUID id, HttpSession session) {

        ModelAndView model = new ModelAndView();

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            model.setViewName("redirect:/login");
            return model;
        }

        UserDto user = userService.getById(userId);

        if (!user.getRole().name().equals("ADMIN")) {
            model.setViewName("redirect:/my-bookings?error=no-permission");
            return model;
        }

        bookingService.updateStatus(id, BookingStatus.CONFIRMED);

        model.setViewName("redirect:/my-bookings");
        return model;
    }

    @PostMapping("/booking/preview")
    public ModelAndView previewBooking(@ModelAttribute BookingRequestDto bookingRequestDto,
                                       HttpSession session) {

        ModelAndView model = new ModelAndView();

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            model.setViewName("redirect:/login");
            return model;
        }

        UserDto user = userService.getById(userId);
        DestinationDto destination = destinationService.getById(bookingRequestDto.getDestinationId());

        int persons;
        if (bookingRequestDto.getPersons() != null) {
            persons = bookingRequestDto.getPersons();
        } else {
            persons = 1;
        }

        BigDecimal totalPrice = destination.getPrice()
                .multiply(BigDecimal.valueOf(persons));

        bookingRequestDto.setPersons(persons);

        model.setViewName("booking");
        model.addObject("user", user);
        model.addObject("destination", destination);
        model.addObject("bookingRequestDto", bookingRequestDto);
        model.addObject("totalPrice", totalPrice);

        return model;
    }

    @GetMapping("/booking/edit/{id}")
    public ModelAndView editPage(@PathVariable UUID id,
                                 HttpSession session) {

        ModelAndView model = new ModelAndView();

        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            model.setViewName("redirect:/login");
            return model;
        }

        UserDto user = userService.getById(userId);

        BookingDto booking = bookingService.getById(id);

        model.setViewName("edit-booking");
        model.addObject("user", user);
        model.addObject("bookingEditDto", BookingMapper.toEditDto(booking));


        return model;
    }

    @PostMapping("/booking/edit/{id}")
    public ModelAndView editBooking(
            @PathVariable UUID id,
            @Valid @ModelAttribute BookingEditDto bookingEditDto,
            BindingResult bindingResult,
            HttpSession session) {

        ModelAndView model = new ModelAndView();

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) {
            model.setViewName("redirect:/login");
            return model;
        }

        if (bookingEditDto.getStartDate() != null &&
                bookingEditDto.getEndDate() != null) {

            if (bookingEditDto.getStartDate().isBefore(LocalDate.now())) {
                bindingResult.rejectValue(
                        "startDate",
                        "date.invalid",
                        "Start date cannot be before today"
                );
            }

            if (bookingEditDto.getEndDate().isBefore(bookingEditDto.getStartDate())) {
                bindingResult.rejectValue(
                        "endDate",
                        "date.invalid",
                        "End date cannot be before start date"
                );
            }
        }

        if (bindingResult.hasErrors()) {
            model.setViewName("edit-booking");
            model.addObject("user", userService.getById(userId));
            model.addObject("bookingEditDto", bookingEditDto);
            return model;
        }

        bookingService.editBooking(id, bookingEditDto);

        model.setViewName("redirect:/my-bookings");
        return model;
    }
}