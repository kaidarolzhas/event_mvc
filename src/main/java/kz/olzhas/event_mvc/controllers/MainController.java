package kz.olzhas.event_mvc.controllers;

import kz.olzhas.event_mvc.models.Event;
import kz.olzhas.event_mvc.models.EventRegistration;
import kz.olzhas.event_mvc.models.Person;
import kz.olzhas.event_mvc.services.EventService;
import kz.olzhas.event_mvc.services.PeopleService;
import kz.olzhas.event_mvc.services.EventRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MainController {

    private final PeopleService peopleService;
    private final EventService eventService;
    private final EventRegistrationService eventRegistrationService;

    @Autowired
    public MainController(PeopleService peopleService, EventService eventService, EventRegistrationService eventRegistrationService) {
        this.peopleService = peopleService;
        this.eventService = eventService;
        this.eventRegistrationService = eventRegistrationService;
    }

    private void checkStatus(List<Event> events){
        for (Event event : events) {
            if(event.getStartTime().isAfter(LocalDateTime.now())){
                event.setStatus("ACTIVE");
            } else {
                event.setStatus("OVERDUE");
            }
        }
    }

    @GetMapping("/")
    public String about(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.findByUsername(authentication.getName());
        String role = "guest";
        if (person != null && person.getRole().equals("ROLE_ADMIN")) {

            return "redirect:/admin";
        }
        if(person != null && person.getRole().equals("ROLE_USER")){
            role = "user";
        }

        List<Event> events = eventService.findAllEvents();
        checkStatus(events);

        m.addAttribute("events", events);
        m.addAttribute("role", role);

        return "user/about.html";
    }

    @GetMapping("/events")
    public String tickets(Model m) {
        List<Event> events = eventService.findAllEvents();
        checkStatus(events);

        m.addAttribute("events", events);

        return "user/events.html";
    }



    @PostMapping("/search")
    public String searchValue(Model m, @RequestParam("search_value1") String search_value1, @RequestParam("search_value2") String search_value2) {
        List<Event> eventList;

        if (!search_value1.isEmpty() || !search_value2.isEmpty()) {
            if (!search_value1.isEmpty() && !search_value2.isEmpty()) {
                eventList = eventService.findByVenueAndSponsor(search_value1, search_value2);
            } else if (!search_value1.isEmpty()) {
                eventList = eventService.findByVenue(search_value1);
            } else {
                eventList = eventService.findBySponsor(search_value2);
            }
        } else {
            eventList = eventService.findAllEvents();
        }

        checkStatus(eventList);
        m.addAttribute("events", eventList);
        return "user/events.html";
    }

    @GetMapping(value = "/my_events")
    public String myBasket(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.findByUsername(authentication.getName());

        List<EventRegistration> ticketOrders = eventRegistrationService.findByPerson(person);

        for (EventRegistration ticketOrder : ticketOrders) {
            if(ticketOrder.getEvent().getStartTime().isAfter(LocalDateTime.now())){
                ticketOrder.getEvent().setStatus("ACTIVE");
            } else {
                ticketOrder.getEvent().setStatus("OVERDUE");
            }
        }

        model.addAttribute("myOrders", ticketOrders);

        return "user/myOrders";
    }


    @GetMapping(value = "/profile")
    public String myProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Person person = peopleService.findByUsername(authentication.getName());
        model.addAttribute("person", person);

        return "user/profile";
    }

    @GetMapping(value = "/profile/edit")
    public String profileEdit(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Person person = peopleService.findByUsername(authentication.getName());
        model.addAttribute("person", person);

        return "user/profileEdit";
    }

    @PatchMapping("/profile")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/profileEdit";
        }

        peopleService.updatePerson(person.getId(), person);
        return "redirect:/profile";
    }

    @GetMapping("/events/order/{id}")
    public String order(Model model, @PathVariable("id") int id, @ModelAttribute("order") EventRegistration eventRegistration) {
        model.addAttribute("event_id", eventService.findOne(id).getId());
        model.addAttribute("event_count", eventService.findOne(id).getCountTicket());

        return "user/order";
    }

    @PostMapping(value = "/events/order")
    public String addOrder(@ModelAttribute("order") @Valid EventRegistration eventRegistration, BindingResult bindingResult,
                           @RequestParam("count") int count,
                           @RequestParam("event_id") int id){
        if (bindingResult.hasErrors()) {
            return "user/order";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.findByUsername(authentication.getName());

        eventRegistration.setCount(count);
        eventRegistration.setPerson(person);
        eventRegistration.setTotalPrice(count * eventService.findOne(id).getPrice());
        eventRegistration.setEvent(eventService.findOne(id));
        eventRegistration.getEvent().setCountTicket(eventService.findOne(id).getCountTicket() - count);

        eventRegistrationService.saveOrder(eventRegistration);

        return "redirect:/";
    }
}
