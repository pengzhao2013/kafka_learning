package com.yupi.kafka;

import com.yupi.kafka.config.KafkaProperties;
import com.yupi.kafka.domain.Student;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @SneakyThrows
    public void sendMessages() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getKeySerializer());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getValueSerializer());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MySerializer.class);
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
        List<String> interceptors = new ArrayList<>();
        interceptors.add("com.yupi.kafka.interceptor.TimestampInterceptor");
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
        KafkaProducer<String, Student> producer = new KafkaProducer<>(properties);
        String topic = kafkaProperties.getProducer().getTopic();
        
        System.out.println("Sending messages to topic: " + topic);
        System.out.println("Using Kafka broker: " + kafkaProperties.getBootstrapServers());
        
        for (int i = 75; i < 80; i++) {
            Student student = new Student();
            student.setName("name-" + i);
            student.setAge(i);
//            String json = new ObjectMapper().writeValueAsString(student);
            ProducerRecord<String, Student> record = new ProducerRecord<>(topic,
                    "key-" + i,
                    student);
            producer.send(record);
            System.out.println("Sent message: key" + i + ", value" + new Date().getTime() + i);
        }
        
        producer.close();
        System.out.println("Messages sent successfully!");
    }
}
