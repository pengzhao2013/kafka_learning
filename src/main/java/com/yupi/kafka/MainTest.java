package com.yupi.kafka;

import com.yupi.kafka.config.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

/**
 * Kafka Producer Test using Spring Boot Configuration
 * 
 * @author canyon.zhao
 * @date 2025/10/13 10:03
 */
@Component
public class MainTest implements CommandLineRunner {
    
    @Autowired
    private KafkaProperties kafkaProperties;
    
    @Override
    public void run(String... args) throws Exception {
        sendMessages();
    }
    
    public void sendMessages() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getKeySerializer());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getValueSerializer());
        
        // Optional configurations from YAML
        if (kafkaProperties.getProducer().getAcks() != null) {
            properties.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());
        }
        if (kafkaProperties.getProducer().getRetries() != null) {
            properties.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getProducer().getRetries());
        }
        if (kafkaProperties.getProducer().getBatchSize() != null) {
            properties.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getProducer().getBatchSize());
        }
        if (kafkaProperties.getProducer().getLingerMs() != null) {
            properties.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.getProducer().getLingerMs());
        }
        if (kafkaProperties.getProducer().getBufferMemory() != null) {
            properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProperties.getProducer().getBufferMemory());
        }
        
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        String topic = kafkaProperties.getProducer().getTopic();
        
        System.out.println("Sending messages to topic: " + topic);
        System.out.println("Using Kafka broker: " + kafkaProperties.getBootstrapServers());
        
        for (int i = 20; i < 25; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic,
                    "key" + i,
                    "value" + new Date().getTime() + i);
            producer.send(record);
            System.out.println("Sent message: key" + i + ", value" + new Date().getTime() + i);
        }
        
        producer.close();
        System.out.println("Messages sent successfully!");
    }
}
