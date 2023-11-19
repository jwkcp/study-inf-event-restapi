package me.whiteship.demoinflearnrestapi.events;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Event {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime begineEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime begineEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    private EventStatus eventStatus;
}
