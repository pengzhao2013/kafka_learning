package com.yupi.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author canyon.zhao
 * @Date 2025/10/27 13:47
 */
public class MyPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        Integer partsNum = cluster.partitionCountForTopic(topic);
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        if (key.toString().startsWith("key-1")) {
            return 1; // key-1,key-11
        } else {
            int part = Utils.toPositive(Utils.murmur2(keyBytes)) % partsNum;
            if (part == 1) {
                return 0;
            }
            return part;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
