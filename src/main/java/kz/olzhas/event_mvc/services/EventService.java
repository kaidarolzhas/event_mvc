package kz.olzhas.event_mvc.services;

import kz.olzhas.event_mvc.models.Event;
import kz.olzhas.event_mvc.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository ticketRepository;

    @Autowired
    public EventService(EventRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void saveEvent(Event event) {
        ticketRepository.save(event);
    }

    public List<Event> findAllEvents() {
        return ticketRepository.findAll();
    }

    public List<Event> findByVenueAndSponsor(String venue, String sponsor){
        return ticketRepository.findByVenueAndSponsor(venue, sponsor);
    }
    public List<Event> findByVenue(String venue){
        return ticketRepository.findByVenue(venue);
    }

    public List<Event> findBySponsor(String sponsor){
        return ticketRepository.findBySponsor(sponsor);
    }

    public Event findOne(int id){
        Optional<Event> ticket = ticketRepository.findById(id);
        return ticket.orElse(null);
    }

    @Transactional
    public void deleteEvent(int id){
        ticketRepository.deleteById(id);
    }

    @Transactional
    public void updateEvent(Long id, Event event){
        event.setId(id);
        ticketRepository.save(event);
    }
}
