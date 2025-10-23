package com.yupi.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupi.kafka.domain.Student;
import lombok.SneakyThrows;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * @Description TODO
 * @Author canyon.zhao
 * @Date 2025/10/22 21:41
 */
public class MySerializer implements Serializer<Student> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
//        Serializer.super.configure(configs, isKey);
    }

    @Override
    @SneakyThrows
    public byte[] serialize(String s, Student student) {
        return new ObjectMapper().writeValueAsBytes(student);
    }

    @Override
    @SneakyThrows
    public byte[] serialize(String topic, Headers headers, Student data) {
        return new ObjectMapper().writeValueAsBytes(data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
