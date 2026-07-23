package edu.nanzhi.util;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.errors.RetriableException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * Kafka的工具类
 */
public class KafkaHelper {

    /**
     * 实例化kafka的连接配置
     * @return
     */
    public Properties initConf(){
        Properties prop = new Properties();

        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node01:9092,node02:9092,node03:9092");
        //avro、protobuf、java实现序列化器(字符串序列化)
        prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //返回prop
        return prop;
    }
    /**
     *Producer实例化封装
     * @return
     */
    public KafkaProducer<String,String> initProducer(){
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node01:9092,node02:9092,node03:9092");
        prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("acks","0");  //不保证数据丢失，能保障吞吐量
        //prop.put(ProducerConfig.ACKS_CONFIG,"all");  //能保障数据不丢不重复，但是性能低一些


        //构建实例
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(prop);
        //返回实例
        return kafkaProducer;
    }

    /**
     * 封装生产
     * @param kafkaProducer
     * @param topic
     * @param i
     */
    public void forgetandSend(KafkaProducer kafkaProducer,String topic,String i){
        //生产
        kafkaProducer.send(new ProducerRecord(topic,i));
    }

    /**
     * 同步发送消息
     * @param kafkaProducer
     * @param topic
     * @param message
     */
    public void syncandSend(KafkaProducer kafkaProducer,String topic,String message) {
        //生产数据
        Future<RecordMetadata> send = kafkaProducer.send(new ProducerRecord(topic, message));
        //阻塞
        try {
            RecordMetadata recordMetadata = send.get();
            System.out.println("sync同步发送数据的分区-offset："+recordMetadata.partition()+"-"+recordMetadata.offset());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println(message+"数据处理中断异常.......");
            kafkaProducer.close();
            throw new RuntimeException(e);
        }
        System.out.println(message+" 消息sync发送成功.");
        try {
            //睡眠
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步发送
     * @param kafkaProducer
     * @param topic
     * @param message
     */
    public void asyncandSend(KafkaProducer kafkaProducer,String topic,String message) {
        //生产数据
        Future<RecordMetadata> send = kafkaProducer.send(new ProducerRecord(topic, message), new Callback() {
            //发送消息完成做操作方法
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                //判断异常是否为空
                if(e != null){
                    System.out.println("发生非中断异常处理......"+message);
                    e.printStackTrace();
                    //判断e的类型
                    if(e instanceof RetriableException){
                        System.out.println("可重试异常处理："+message);
                    } else {
                        System.out.println("不可重试异常处理："+message);
                    }
                } else {
                    System.out.println("消息发送成功："+message+",元数据信息的分区为：" +
                            ""+recordMetadata.partition()+",offset为"+recordMetadata.offset());
                }
            }
        });
        //睡眠
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}