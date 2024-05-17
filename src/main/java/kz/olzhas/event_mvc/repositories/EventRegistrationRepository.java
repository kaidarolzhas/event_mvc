package kz.olzhas.event_mvc.repositories;

import kz.olzhas.event_mvc.models.EventRegistration;
import kz.olzhas.event_mvc.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    List<EventRegistration> findByPerson (Person person);
}
