package edu.nanzhi.util;

import org.apache.kafka.clients.consumer.ConsumerGroupMetadata;
import org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignor;
import org.apache.kafka.common.TopicPartition;

import java.nio.ByteBuffer;
import java.util.*;

public class CustormAssignorTest extends AbstractPartitionAssignor {


    @Override
    public Map<String, List<TopicPartition>> assign(Map<String, Integer> topicPatitions,
                                                    Map<String, Subscription> consumerTopics) {
        HashMap<String,List<TopicPartition>> consumer_tps = new HashMap<>();
        for (String consumer : consumerTopics.keySet()){
            consumer_tps.put(consumer,new ArrayList<>());
        }
        Map<String, List<String>> topic_consumers = topocConsumers(consumerTopics);
        //循环topic_conumers
        for (Map.Entry<String, List<String>> topic_consumer : topic_consumers.entrySet()) {
            //获取主题
            String topic = topic_consumer.getKey();
            //获取消费者
            List<String> consumers = topic_consumer.getValue();
            //获取分区
            Integer t_ps = topicPatitions.get(topic);
            if (t_ps == null){
           continue;
       }
            List<TopicPartition> partitions = partitions(topic, t_ps);
            //将分区随机分配给消费者（）
            for (TopicPartition partition : partitions){
                int rendomIndex = new Random().nextInt(consumers.size());
                String rc = consumers.get(rendomIndex);//根据随机索引获取对应consumers
                consumer_tps.get(rc).add(partition);
                System.out.println("topic"+topic+","+rc+"--->"+partition+",index="+rendomIndex);
            }
        }

        return consumer_tps;
    }

    @Override
    public ByteBuffer subscriptionUserData(Set<String> topics) {
        return super.subscriptionUserData(topics);
    }

    @Override
    public void onAssignment(Assignment assignment, ConsumerGroupMetadata metadata) {
        super.onAssignment(assignment, metadata);
    }

    @Override
    public List<RebalanceProtocol> supportedProtocols() {
        return super.supportedProtocols();
    }

    @Override
    public short version() {
        return super.version();
    }

    @Override
    public String name() {
        return null;
    }


    public Map<String, List<String>> topocConsumers(Map<String, Subscription> consumerTopics){
        HashMap<String,List<String>> map = new HashMap<>();
        for (Map.Entry<String,Subscription> topic_Consumer : consumerTopics.entrySet()){
             String consumer = topic_Consumer.getKey();
             List<String> topics = topic_Consumer.getValue().topics();
             //将toipics循环封装到map中
             for (String topic : topics){
                 put(map,topic,consumer);

             }
        }
        return map;
    }
}

