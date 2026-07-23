package edu.nanzhi.IDIdenfier;

import edu.nanzhi.util.KafkaHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;

/**
 * 向kafka发送数据并测试，建议造一条正确身份信息数据和一条错误身份信息数据进行测试
 */
public class IDIdentifierTest {
    public static void main(String[] args) {
        //获取配置信息
        Properties prop = new KafkaHelper().initConf();
        //将自定义分区器进行设置
        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"edu.nanzhi.IDIdenfier.ApplicationInfoPartitioner");
        //设置序列化器
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"edu.nanzhi.IDIdenfier.ApplicationInfoSerilizer");
        //设置拦截器
        prop.setProperty(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,"edu.nanzhi.IDIdenfier.IdentifierInterceptor");
        //实例化生产者
        KafkaProducer<String, ApplicationInfo> kafkaProducer = new KafkaProducer<>(prop);
        //模拟生产数据测试
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1);
        applicationInfo.setIdcard("");  //添加正确的身份证号码
        applicationInfo.setApplayTime(new Date().getTime());
        ProducerRecord<String, ApplicationInfo> record = new ProducerRecord<>("test", applicationInfo);
        //将封装好的数据进行发送
        kafkaProducer.send(record);

        //模拟错误的数据测试
        ApplicationInfo applicationInfo1 = new ApplicationInfo();
        applicationInfo1.setId(2);
        applicationInfo1.setIdcard("123121199001106786");  //添加错误的身份证号码
        applicationInfo1.setApplayTime(new Date().getTime());
        ProducerRecord<String, ApplicationInfo> record1 = new ProducerRecord<>("test", applicationInfo1);
        //将封装好的数据进行发送
        kafkaProducer.send(record1);

        //关闭生产者
        kafkaProducer.close();
    }
}
