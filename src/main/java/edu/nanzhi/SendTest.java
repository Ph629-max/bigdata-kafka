package edu.nanzhi;

import edu.nanzhi.util.KafkaHelper;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * 生产消息测试
 */
public class SendTest {
    public static void main(String[] args) {
        /**
         * 发送并忘记测试
         */
        KafkaHelper kafkaHelper = new KafkaHelper();
        Properties prop = kafkaHelper.initConf();
        KafkaProducer<String, String> producer = kafkaHelper.initProducer();
        for (int i=0;i<6;i++){
            //kafkaHelper.forgetandSend(producer,"kafka-send-test",i+"");
        }

        /**
         * 同步发送
         */
        /*for (int i=0;i<6;i++){
            kafkaHelper.syncandSend(producer,"kafka-send-test","sync-"+i);
        }*/

        /**
         * 异步发送测试
         */
        for (int i=0;i<6;i++){
            kafkaHelper.asyncandSend(producer,"kafka-send-test","async-"+i);
        }

        //关闭
        producer.close();
    }
}
