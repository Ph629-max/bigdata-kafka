package edu.nanzhi;

import edu.nanzhi.util.KafkaHelper;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * PrefixProducerInterceptor自定义拦截器测试
 */
public class MyInterceptorTest {
    public static void main(String[] args) {
        //配置相关属性
        Properties prop = new KafkaHelper().initConf();
        //将拦截器信息设置到如下的prop对象中:interceptor.classes
        prop.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,edu.nanzhi.PrefixProducerInterceptor.class.getName());
        //构造带有拦截器的生产者实例
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(prop);
        //构造消息并发送消息
        for(int i = 0;i<=3;i++){
            kafkaProducer.send(new ProducerRecord<String,String>("interceptor-test","msg"+i));
        }
        //关闭kafka的生产者
        kafkaProducer.close();
    }
}
