package edu.nanzhi;

import edu.nanzhi.util.KafkaHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;

/**
 * 序列化测试
 */
public class UserSerializerTest {
    public static void main(String[] args) {
        //获取kafka的配置信息
        KafkaHelper kafkaHelper = new KafkaHelper();
        Properties prop = kafkaHelper.initConf();
        //修改成自己的序列化
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"edu.nanzhi.UserSerializer");
        //获取对应的生产者
        KafkaProducer<String, User> kafkaProducer = new KafkaProducer<>(prop);
        //循环生产3条数据
        for(int i =0 ;i < 3 ; i++){
            //构造user
            User user = new User(i, "name" + i, new Random().nextInt(100), "女");
            //封装数据
            ProducerRecord<String, User> userProducerRecord = new ProducerRecord<>("serializer-test", user);
            Future<RecordMetadata> send = kafkaProducer.send(userProducerRecord);
        }
        //关闭生产者
        kafkaProducer.close();
    }
}
