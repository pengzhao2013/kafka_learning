package com.yupi.kafka.interceptor.consumer;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Map;

/**
 * @Description TODO
 * @Author canyon.zhao
 * @Date 2025/11/2 13:51
 */
public class ConInterceptor implements ConsumerInterceptor {
    @Override
    public ConsumerRecords onConsume(ConsumerRecords records) {
        System.out.println("ConsumerRecords:" + records);
        return records;
    }

    @Override
    public void close() {

    }

    @Override
    public void onCommit(Map offsets) {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
