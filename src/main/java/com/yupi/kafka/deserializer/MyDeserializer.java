package com.yupi.kafka.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupi.kafka.domain.Student;
import lombok.SneakyThrows;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * @Description TODO
 * @Author canyon.zhao
 * @Date 2025/11/2 11:38
 */
public class MyDeserializer implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    @SneakyThrows
    public Object deserialize(String topic, byte[] data) {
        return new ObjectMapper().readValue(data, Student.class);
    }

    @Override
    @SneakyThrows
    public Object deserialize(String topic, Headers headers, byte[] data) {
        return new ObjectMapper().readValue(data, Student.class);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
