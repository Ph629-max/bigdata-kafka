package edu.nanzhi;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * 将数据中的key值模分区数，等于0的一个分区，等于1一个分区，等于2的一个分区
 * 分区自定义步骤：
 * 1、实现partitioner接口
 * 2、实现partition方法
 */
public class MyPartitioner implements Partitioner {
    /**
     *
     * @param s 输入的主题
     * @param o 输入key
     * @param bytes  输入key对应的字节数组
     * @param o1  输入的value值
     * @param bytes1  输入value的字节数组
     * @param cluster  kafka的集群参数
     * @return
     */
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        //获取s主题的分区数
        List<PartitionInfo> partitionInfos = cluster.availablePartitionsForTopic(s);
        //根据key的数值模分区数来对数据进行分配
        int partitionID = Integer.parseInt(o.toString()) % partitionInfos.size();
        System.out.println("key:"+o.toString() + "的数据将存储到:"+partitionID+" 分区中......");
        //返回分区标识
        return partitionID;
    }
    @Override
    public void close() {
        System.out.println("my partitioner close......");
    }
    @Override
    public void configure(Map<String, ?> map) {
        System.out.println("my partitioner configure......");
    }
}
