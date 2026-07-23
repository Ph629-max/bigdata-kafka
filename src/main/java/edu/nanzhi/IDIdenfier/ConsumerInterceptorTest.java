package edu.nanzhi.IDIdenfier;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

public class ConsumerInterceptorTest implements ConsumerInterceptor {

    @Override
    public ConsumerRecords onConsume(ConsumerRecords consumerRecords) {
        //定义一个hasinmap
        HashMap<TopicPartition, List<ConsumerRecords<String,String>>> map = new HashMap<>();
        Set<TopicPartition> partitions = consumerRecords.partitions();
        for (TopicPartition partition : partitions){
            //获取对应分区partition的数据
            List <ConsumerRecords<String,String>>records = consumerRecords.records(partition);
            List<ConsumerRecords<String,String>> new_records = new ArrayList<>();
            for (ConsumerRecords<String,String> record : records){
                String v = (String) record.value();
                if (v.contains("女")){
                    new_records.add(record);
                }
            }
            if (!new_records.isEmpty()){
                map.put(partition,new_records);
            }
            return new ConsumerRecords(map);

        }

        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void onCommit(Map map) {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
