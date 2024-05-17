package kz.olzhas.event_mvc.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_registration")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRegistration {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "count")
    private int count;

    @NotEmpty(message = "The number of phone must not be a empty")
    @Size(min = 2, max = 100, message = "The number of phone must be around 2 and 100")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
}
