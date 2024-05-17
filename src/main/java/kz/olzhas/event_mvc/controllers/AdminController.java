package kz.olzhas.event_mvc.controllers;

import kz.olzhas.event_mvc.models.Event;
import kz.olzhas.event_mvc.models.EventRegistration;
import kz.olzhas.event_mvc.services.EventService;
import kz.olzhas.event_mvc.services.EventRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final EventService eventService;
    private final EventRegistrationService ordersService;




    @GetMapping("/add")
    public String newTicket(@ModelAttribute("event") Event event) {
        return "admin/add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("event") @Valid Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/add";
        }

        eventService.saveEvent(event);

        return "redirect:/";
    }

    @GetMapping()
    public String main(Model model) {
        List<Event> eventList = eventService.findAllEvents();
        checkStatus(eventList);
        model.addAttribute("allEvent", eventList);
        return "admin/index";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        eventService.deleteEvent(id);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("event", eventService.findOne(id));

        return "admin/update";
    }

    @PatchMapping("/update/{id}")
    public String update(@ModelAttribute("event") @Valid Event event, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "admin/update";
        }

        eventService.updateEvent(id, event);
        return "redirect:/";
    }

    @GetMapping("/order")
    public String allOrder(Model model) {
        List<EventRegistration> eventRegistrations = ordersService.findAllOrder();

        for (EventRegistration eventRegistration : eventRegistrations) {
            if(eventRegistration.getEvent().getStartTime().isAfter(LocalDateTime.now())){
                eventRegistration.getEvent().setStatus("ACTIVE");
            } else {
                eventRegistration.getEvent().setStatus("OVERDUE");
            }
        }

        model.addAttribute("orders", eventRegistrations);

        return "admin/order";
    }

    private void checkStatus(List<Event> eventList){
        for (Event event : eventList) {
            if(event.getStartTime().isAfter(LocalDateTime.now())){
                event.setStatus("ACTIVE");
            } else {
                event.setStatus("OVERDUE");
            }
        }
    }
}
