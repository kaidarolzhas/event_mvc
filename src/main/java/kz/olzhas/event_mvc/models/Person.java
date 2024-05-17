package kz.olzhas.event_mvc.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
@Builder
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "The name should not be empty")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters long")
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty(message = "The surname should not be empty")
    @Size(min = 2, max = 100, message = "The surname must be between 2 and 100 characters long")
    @Column(name = "lastname")
    private String lastname;

    @NotEmpty(message = "The login should not be empty")
    @Size(min = 2, max = 100, message = "The login must be between 2 and 100 characters long")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "The password should not be empty")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    private String dateOfBorn;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<EventRegistration> eventRegistrations;
}
