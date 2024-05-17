package kz.olzhas.event_mvc.services;

import kz.olzhas.event_mvc.models.Person;
import kz.olzhas.event_mvc.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Person findByUsername(String s){
        Optional<Person> person = peopleRepository.findByUsername(s);

        return person.orElse(null);
    }

    @Transactional
    public void savePerson(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson){
        updatedPerson.setId(id);

        peopleRepository.save(updatedPerson);
    }
}
