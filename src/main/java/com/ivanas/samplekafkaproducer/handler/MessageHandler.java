package com.ivanas.samplekafkaproducer.handler;


import com.ivanas.samplekafkaproducer.service.MessageKafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
public class MessageHandler {
    private final MessageKafkaProducerService kafkaProducerServer;

    @NotNull
    public Mono<ServerResponse> postMessage(final ServerRequest serverRequest) {
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(BodyInserters.fromPublisher(serverRequest.bodyToMono(String.class)
                        .flatMap(kafkaProducerServer::publishToKafka), Boolean.class));
    }
}
