package edu.nanzhi.IDIdenfier;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * 自定义分区器，将不同地区的数据发送到对应的分区
 */
public class ApplicationInfoPartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        int pnum = 0;
        //通过集群获取主题s的分区数量
        List<PartitionInfo> partitionInfos = cluster.availablePartitionsForTopic(s);
        //判断输入的value是否为PartitionInfo
        if(o1 instanceof ApplicationInfo){
            //获取前2位地区码
            String areaCode = ((ApplicationInfo) o1).getIdcard().substring(0, 2);
            //将地区码转换为整数
            int i = Integer.parseInt(areaCode);
            //计算分区编号
            pnum = i % partitionInfos.size();
        }
        return pnum;
    }

    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void configure(Map<String, ?> map) {
        System.out.println("configure");
    }
}
