package me.whiteship.demoinflearnrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EventRepository eventRepository;

    private static final MediaType HAL_JSON = MediaType.valueOf("application/hal+json");

    @Test
    void createEvent() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 16, 12))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 21, 16, 12))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 15, 20, 10))
                .endEventDateTime(LocalDateTime.of(2018, 12, 15, 20, 10))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 2번 출구")
                .build();

//        event.setId(10);
//        Mockito.when(eventRepository.save(event)).thenReturn(event);
        // event와 동일한 객체가 아니라 Event타입이면 어떤 객체가 와도 상관없다는 Mokito로 사용

//        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"))
                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andExpect(header().string("Content-Type", "application/hal+json"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)));
    }
}
