package kz.olzhas.event_mvc.services;

import kz.olzhas.event_mvc.models.EventRegistration;
import kz.olzhas.event_mvc.models.Person;
import kz.olzhas.event_mvc.repositories.EventRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventRegistrationService {
    private final EventRegistrationRepository eventRegistrationRepository;

    @Autowired
    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
    }

    @Transactional
    public void saveOrder(EventRegistration eventRegistration){
        eventRegistrationRepository.save(eventRegistration);
    }

    public List<EventRegistration> findByPerson(Person person) {
        return eventRegistrationRepository.findByPerson(person);
    }

    public List<EventRegistration> findAllOrder() {
        return eventRegistrationRepository.findAll();
    }
}
