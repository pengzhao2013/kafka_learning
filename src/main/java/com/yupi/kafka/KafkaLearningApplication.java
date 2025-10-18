package com.yupi.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Kafka Learning Application
 * 
 * @author canyon.zhao
 */
@SpringBootApplication
@EnableConfigurationProperties
public class KafkaLearningApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(KafkaLearningApplication.class, args);
    }
}