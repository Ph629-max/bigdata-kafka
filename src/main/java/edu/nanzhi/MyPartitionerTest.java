package edu.nanzhi;

import edu.nanzhi.util.KafkaHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 分区器测试类
 */
public class MyPartitionerTest {
    public static void main(String[] args) {
        //获取配置信息
        Properties prop = new KafkaHelper().initConf();
        //将分区器进行应用
        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"edu.nanzhi.MyPartitioner");
        //实例化生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(prop);
        //生产数据测试
        for (int i = 0; i < 10 ; i++){
            //封装数据
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    "partitioner-test",
                    i + "",
                    "value_" + i);
            //将封装好的数据进行发送
            kafkaProducer.send(record);
        }
        //关闭生产者
        kafkaProducer.close();
    }
}
