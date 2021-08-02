package com.ivanas.samplekafkaproducer.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:9093", "port=9093"}, topics = "app.local.alert")
@ActiveProfiles("standalone")
class MessageKafkaProducerServiceTest {

    ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaProducerTemplate;

    @Autowired
    MessageKafkaProducerService messageKafkaProducerService;

    @BeforeEach
    void startUp() {
        Map<String, Object> props = new HashMap<>(0);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "TestGroup");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        reactiveKafkaProducerTemplate = new ReactiveKafkaConsumerTemplate<>(ReceiverOptions.create(props));
    }

    @Test
    void shouldSendMessageToKafka() {
        //Given-When
        messageKafkaProducerService.publishToKafka("anyMessage");
        //Then
        await().atMost(2, SECONDS).untilAsserted(() -> {
            Mono<String> mono = Mono.empty();
            Function<String, Mono<String>> createMono = string -> mono.just(string);
            reactiveKafkaProducerTemplate.receiveAutoAck()
                    .map(ConsumerRecord::value)
                    .doOnNext(createMono::apply)
                    .subscribe();

            StepVerifier.create(mono)
                    .verifyComplete();
        });
    }

}