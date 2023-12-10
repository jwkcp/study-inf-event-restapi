package me.whiteship.demoinflearnrestapi.events;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@Controller
//public class EventController {
//    @PostMapping("/api/events")
//    public ResponseEntity createEvent(@RequestBody Event event) {
//        URI createdUri = linkTo(methodOn(EventController.class).createEvent(null)).slash("{id}").toUri();
//        return ResponseEntity.created(createdUri).build();
//    }
//}


@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventDto eventDto) {
        Event event = modelMapper.map(eventDto, Event.class);
        Event savedEvent = eventRepository.save(event);
        URI createdUri = linkTo(EventController.class).slash(savedEvent.getId()).toUri();
//        eventDto.setId(10);
        return ResponseEntity.created(createdUri).body(event);
    }
}
