package com.ivanas.samplekafkaproducer.handler;

import com.ivanas.samplekafkaproducer.config.HandlerConfig;
import com.ivanas.samplekafkaproducer.service.MessageKafkaProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import(HandlerConfig.class)
class MessageHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MessageKafkaProducerService messageKafkaProducerService;

    @Test
    void shouldReturnOkResponse() {
        //Given-When
        when(messageKafkaProducerService.publishToKafka("anyMessage")).thenReturn(Mono.just(Boolean.TRUE));
        //Then
        webTestClient.post()
                .uri("http://localhost:8080/message")
                .body(BodyInserters.fromValue("anyMessage"))
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .equals(true);
    }

}