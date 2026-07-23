package edu.nanzhi.IDIdenfier;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * 自定义拦截器，借助validateIDCardUtil来拦截不正确的身份信息
 */
public class IdentifierInterceptor implements ProducerInterceptor<String,ApplicationInfo> {
    //定义常量
    private static final String PREFIX = "010_";
    private int errorCounter = 0;
    private int sucessCounter = 0;
    @Override
    public ProducerRecord<String, ApplicationInfo> onSend(ProducerRecord<String, ApplicationInfo> producerRecord) {
        System.out.println("过滤身份信息......");
        ApplicationInfo userInfo = producerRecord.value();
        String idcard = userInfo.getIdcard(); //获取idcard
        //定义ProducerRecord变量
        ProducerRecord result = null;
        //判断身份ID是否合法,判断逻辑交给工具类validateIDCardUtil做
        if(ValidateIDCardUtil.isIDCard(idcard)){
            //代码进入该区域，则idcard合法，对于合法idcard则发送到kafka主题即可
            result = new ProducerRecord<>(producerRecord.topic(),
                    producerRecord.partition(),
                    producerRecord.key(),
                    userInfo
            );
        }
        return result;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        System.out.println("接收消息......");
        if(e !=null){
            //e.printStackTrace();
            errorCounter++;
        } else {
            sucessCounter++;
        }
    }

    @Override
    public void close() {
        System.out.println("清理资源......");
        System.out.println("errorCounter:"+errorCounter+",sucessCounter:"+sucessCounter);
    }

    @Override
    public void configure(Map<String, ?> map) {
        System.out.println("修改配置......");
    }
}
