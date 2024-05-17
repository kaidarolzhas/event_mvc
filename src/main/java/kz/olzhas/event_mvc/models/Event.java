package kz.olzhas.event_mvc.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
@Builder
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "AirLine must not to be empty")
    @Size(min = 2, max = 100, message = "Airline must be from 2 to 100 characters long")
    @Column(name = "sponsor")
    private String sponsor;


    @NotEmpty(message = "Type must not to be empty")
    @Size(min = 2, max = 100, message = "Type must be from 2 to 100 characters long")
    @Column(name = "type")
    private String type;

    @NotEmpty(message = "The point of departure should not be empty")
    @Size(min = 2, max = 100, message = "The point of departure must be from 2 to 100 characters long")
    @Column(name = "venue")
    private String venue;

    @NotNull
    @Future(message = "The date should be in the future")
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Min(value = 1, message = "The number of seats must be more than 1")
    @Column(name = "count_ticket")
    private int countTicket;

    @Min(value = 0)
    @Column(name = "price")
    private double price;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventRegistration> eventRegistrations;
}
