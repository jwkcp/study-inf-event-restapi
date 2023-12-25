package me.whiteship.demoinflearnrestapi.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.whiteship.demoinflearnrestapi.common.TestDescription;
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
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

//    @MockBean
//    EventRepository eventRepository;

    private static final MediaType HAL_JSON = MediaType.valueOf("application/hal+json");

    @Test
    @TestDescription(value = "유효하지 않은 값으로 이벤트를 생성하는 테스트")
    void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
//                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 16, 12))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 21, 16, 12))
                .beginEventDateTime(LocalDateTime.of(2018, 12, 15, 20, 10))
                .endEventDateTime(LocalDateTime.of(2018, 11, 15, 10, 10))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 2번 출구")
//                .free(true)
//                .offline(false)
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
//                        .accept(HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
                // Field 에러가 없는 경우 아래 구문으로 테스트가 깨질 수 있음
//                .andExpect(jsonPath("$[0].field").exists())
//                .andExpect(jsonPath("$[0].rejectedValue").exists())
        ;

    }

    @Test
    @TestDescription(value = "입력값이 비어있는 경우에 에러가 발생하는 테스트")
    void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                        .accept(HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @TestDescription(value = "입력받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
    void createEvent_Bad_Request() throws Exception {
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
                .free(true)
                .offline(false)
                .build();

//        event.setId(10);
//        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
//                .andExpect(header().exists("Location"))
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andExpect(header().string("Content-Type", "application/hal+json"))
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
//                .andExpect(jsonPath("id").exists())
//                .andExpect(jsonPath("id").value(Matchers.not(100)))
//                .andExpect(jsonPath("free").value(Matchers.not(true)))
//                .andExpect(jsonPath("eventStatus").value(Matchers.equalTo(EventStatus.DRAFT.name())));
    }

    @Test
    @TestDescription(value = "정상적으로 이벤트를 생성하는 테스트")
    void createEvent() throws Exception {
        EventDto eventDto = EventDto.builder()
//                .id(100)
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
//                .free(true)
//                .offline(false)
                .build();

//        event.setId(10);
//        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"))
                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andExpect(header().string("Content-Type", "application/hal+json"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(Matchers.equalTo(EventStatus.DRAFT.name())));
    }
}
