package com.ivanas.samplekafkaproducer.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class MessageKafkaProducerService {

    private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaTemplate;

    @Value(value = "${spring.kafka.template.default-topic}")
    private String topic;

    public Mono<Boolean> publishToKafka(final String message) {
        return reactiveKafkaTemplate.send(topic, Strings.EMPTY, message)
                .map(result -> result.exception() == null)
                .subscribeOn(Schedulers.newBoundedElastic(2, 2, "AsyncKafkaThread"));
    }

}
