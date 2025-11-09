package com.yupi.kafka;

import com.yupi.kafka.callback.MyCallback;
import com.yupi.kafka.config.KafkaProperties;
import com.yupi.kafka.domain.Student;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, kafkaProperties.getProducer().getPartitioner());
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
        interceptors.add("com.yupi.kafka.interceptor.producer.TimestampInterceptor");
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
        KafkaProducer<String, Student> producer = new KafkaProducer<>(properties);
        String topic = kafkaProperties.getProducer().getTopic();
        
        System.out.println("Sending messages to topic: " + topic);
        System.out.println("Using Kafka broker: " + kafkaProperties.getBootstrapServers());

        new Thread(() -> {
            consumerMessage(topic);
        }, "consumerThread").start();
        Thread.sleep(2000);
        for (int i = 115; i < 120; i++) {
            Student student = new Student();
            student.setName("name-" + i);
            student.setAge(i);
//            String json = new ObjectMapper().writeValueAsString(student);
            ProducerRecord<String, Student> record = new ProducerRecord<>(topic,
                    "key-" + i,
                    student);
            producer.send(record, new MyCallback());
//            System.out.println("Sent message: key" + i + ", value" + new Date().getTime() + i);
        }
        
        producer.close();
        System.out.println("Messages sent successfully!");
    }

    private void consumerMessage(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.yupi.kafka.deserializer.MyDeserializer");
//        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "range,round,sticky");

        List<String> interceptors = new ArrayList<>();
        interceptors.add("com.yupi.kafka.interceptor.consumer.ConInterceptor");
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        KafkaConsumer<String, Student> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            try {
                ConsumerRecords<String, Student> poll = consumer.poll(100);
                for (ConsumerRecord<String, Student> record : poll) {
                    // 数据筛查、幂等校验
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
                            record.value());
                }
//            consumer.commitSync();
                consumer.commitAsync();
            } catch (Exception e) {
                consumer.commitSync();
            }
        }
    }
}
