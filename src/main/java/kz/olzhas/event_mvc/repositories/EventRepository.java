package kz.olzhas.event_mvc.repositories;

import kz.olzhas.event_mvc.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByVenueAndSponsor(String venue, String sponsor);
    List<Event> findByVenue(String venue);
    List<Event> findBySponsor(String sponsor);
}
