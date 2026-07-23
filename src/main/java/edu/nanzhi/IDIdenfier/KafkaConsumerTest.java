package edu.nanzhi.IDIdenfier;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerTest {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("bootstrap", "Hadoop01:8081,Hadoop02:8081,Hadoop03:8081");
        prop.setProperty("group.id", "test-consumer-group");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(prop);
        kafkaConsumer.subscribe(Arrays.asList("test"));//
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(1000);
                for (ConsumerRecord<String, String> crs : consumerRecords) {
                    System.out.println("topic:" + crs.topic() + ",partition:" + crs.partition() + ",offset:" + crs.offset() + crs.key() +
                            ",key" + crs.key() + ",value:" + crs.value());
                }
            }
        } finally {
            kafkaConsumer.close();
        }
    }
}