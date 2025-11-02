package com.yupi.kafka.interceptor.producer;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @Description kafka中拦截器implements ProducerInterceptor
 * @Author canyon.zhao
 * @Date 2025/10/22 10:21
 */
public class TimestampInterceptor implements ProducerInterceptor {
    // 可以覆盖之前的producerRecord
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        // ProducerRecord不允许修改里面的属性值
        // 因为ProducerRecord包含了topic/分区信息/key/value属性
        // 会造成网络传输过程中的不安全

        // 拦截器中对value的修改不能影响对value的序列化
//        return new ProducerRecord(producerRecord.topic(),
//                producerRecord.key(),
//                String.valueOf(producerRecord.value()) + new Date().getTime());

        return new ProducerRecord(producerRecord.topic(),
                producerRecord.key(),
                producerRecord.value());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        // kafka producer会在消息被应答之前或消息发送失败，都会调用
        // 可以监听消息是否发送到kafka 优先于用户设定的callback之前执行 运行在producer I/O线程中 会影响发送速度
        // 有专门的callback机制来监听发送结果
        // 尽量不添加任何逻辑
//        System.out.println("####################" + recordMetadata.offset());
    }

    @Override
    public void close() {
        // 关闭拦截器时后续一些清理工作
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
