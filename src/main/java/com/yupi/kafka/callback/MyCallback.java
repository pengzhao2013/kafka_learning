package com.yupi.kafka.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @Description TODO
 * @Author canyon.zhao
 * @Date 2025/10/30 15:17
 */
public class MyCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        // 即便kafka配置里retry机制，还得靠callback，因为不敢确保kafka的retry能成功
        // 如果失败，最后还得通过onCompletion异步调用结果进行确定
        if (exception != null) {
            System.out.println("exception:" + exception.getMessage());
        }
        System.out.println("topic:" + metadata.topic() + " partition:" + metadata.partition() +
                " offset:" + metadata.offset() + "onCompletion");
    }
}
