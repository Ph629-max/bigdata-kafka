package edu.nanzhi;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 生产者案例
 */
public class ProducerBase {
    public static void main(String[] args) {
        //1、配置Kafka的连接接参数
        Properties prop = new Properties();
        //配置kafka的集群地址信息   --bootstrap-server
        prop.put("bootstrap.servers","node01:9092,node02:9092,node03:9092");
        prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        //2、构建Kafka的生产者实例
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(prop);

        //3、创建和发送消息  --->未指定key和分区；主题需要提前创建
        /*for (int i = 0 ;i<6;i++){
            kafkaProducer.send(new ProducerRecord<>("java_topic","p_"+i));
        }*/

        //指定分区
        /*for(int i=0; i<6 ;i++){
            kafkaProducer.send(new ProducerRecord<>(
                    "java_topic",  //指定生产主题
                    1,   //指定分区
                    "k1",   //指定key
                    "k_"+i));  //指定key对应的值
        }*/

        //指定key
        for(int i=0; i<6 ;i++){
            kafkaProducer.send(new ProducerRecord<>(
                    "java_topic",  //指定生产主题
                    "k1"+i,   //指定key
                    "k_"+i));  //指定key对应的值
        }

        //4、关闭生产者
        kafkaProducer.close();
        System.out.println("消息发送完成！！！");
    }
}
