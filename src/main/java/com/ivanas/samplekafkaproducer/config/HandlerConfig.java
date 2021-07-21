package com.ivanas.samplekafkaproducer.config;

import com.ivanas.samplekafkaproducer.handler.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HandlerConfig {

    @Bean
    public RouterFunction<ServerResponse> getRouter(final MessageHandler messageHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/message"), messageHandler::postMessage);
    }
}
