package edu.nanzhi;

import edu.nanzhi.util.KafkaHelper;
import edu.nanzhi.util.RandomInfo;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * 随机生产用户数据
 */
public class UserInfoProducerTest {
    public static void main(String[] args) throws InterruptedException {
        //获取kafka的配置信息
        KafkaHelper kafkaHelper = new KafkaHelper();
        //获取生产者
        KafkaProducer<String, String> producer = kafkaHelper.initProducer();
        //不定时的随机生产用户信息
        while (true){
            //获取随机的用户信息
            String user_name = RandomInfo.randomInt(10)%2 == 0 ? RandomInfo.getRandomGirlName() : RandomInfo.getRandomBoyName();
            String phone = RandomInfo.getRandomPhone();
            int age = RandomInfo.getRandomAge(0, 130);
            String sex = RandomInfo.getRandomSex();
            String email = RandomInfo.getRandomQQEmail();
            String address = RandomInfo.getRandomAddress();
            String user_info = user_name+","+phone+","+age+","+sex+","+email+","+address;
            System.out.println(user_info);
            //将随机用户信息数据进行发送到kafka
            kafkaHelper.forgetandSend(producer,"kafka-send-test",user_info);
            //随机的不定时，即延迟
            Thread.sleep(RandomInfo.randomInt(100000));
        }
    }
}
