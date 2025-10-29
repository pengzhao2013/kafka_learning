package com.yupi.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Kafka configuration properties
 * 
 * @author canyon.zhao
 */
@Component
@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaProperties {
    
    private String bootstrapServers;
    private Producer producer = new Producer();

    @Data
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
        private String partitioner;
        private String topic;
        private String acks;
        private Integer retries;
        private Integer batchSize;
        private Integer lingerMs;
        private Long bufferMemory;
    }
}