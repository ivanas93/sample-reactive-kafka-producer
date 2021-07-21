package com.ivanas.samplekafkaproducer.handler;

import com.ivanas.samplekafkaproducer.config.HandlerConfig;
import com.ivanas.samplekafkaproducer.service.MessageKafkaProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import(HandlerConfig.class)
class MessageHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MessageKafkaProducerService messageKafkaProducerService;

    @Test
    void init(){

    }

}