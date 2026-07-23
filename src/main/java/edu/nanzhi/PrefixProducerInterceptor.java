package edu.nanzhi;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * 自定义拦截器
 * 1、kafka的拦截器需要实现ProducerInterceptor接口
 * 2、需要实现onSend、onAcknowledgement、close三个方法
 */
public class PrefixProducerInterceptor implements ProducerInterceptor<String,String> {
    //定义消息的前缀
    private static final String PREFIX = "010_";
    //定义2个累加器，累加成功和失败发送消息数量
    private int errorCounter = 0;
    private int scuessCounter = 0;

    /**
     * 负责接收和发送消息，接收上游的数据，经过处理发送到下游
     * @param producerRecord
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        System.out.println("2、修改上游发过来的消息......");
        //  producerRecord.value():获取上游数据的value值
        //将value值修改为：当前线程名称_010_上游数据value值
        String res = Thread.currentThread().getName()+"_"+PREFIX+producerRecord.value();
        //将修改后的值发送下游
        ProducerRecord<String, String> record = null;
        if (Integer.parseInt(producerRecord.value().substring(3,4)) % 2 == 0){
            record = new ProducerRecord<>(
                    producerRecord.topic(),
                    producerRecord.partition(),
                    producerRecord.key(),
                    res);
        } else {
            record = new ProducerRecord<>(
                    producerRecord.topic(),
                    producerRecord.partition(),
                    producerRecord.key(),
                    "");
        }
        return record;
    }


    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        //根据e判断是否有异常，如果有异常则表示错误，否则成功
        System.out.println("3、接收消息信息.......");
        if(e != null){
            e.printStackTrace();
            //有异常，则错误+1
            errorCounter ++;
        } else {
            scuessCounter ++;
        }
    }

    @Override
    public void close() {
        System.out.println("4、清理资源......");
        System.out.println("errorCounter="+errorCounter+",successCounter="+scuessCounter);
    }

    @Override
    public void configure(Map<String, ?> map) {
        System.out.println("1、修改配置信息.......");
    }
}
