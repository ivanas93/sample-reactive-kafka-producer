package com.ivanas.samplekafkaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class SampleKafkaProducerApplication {

    public static void main(String[] args) {
        BlockHound.install();
        SpringApplication.run(SampleKafkaProducerApplication.class, args);
    }

}
