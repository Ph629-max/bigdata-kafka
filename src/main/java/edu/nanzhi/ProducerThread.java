package edu.nanzhi;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.RetriableException;

public class ProducerThread<k,v> implements Runnable{
    //定义kafka的生产者
    private KafkaProducer<k,v> kp;
    //定义kafka的生产数据对象
    private ProducerRecord<k,v> pr;

    //制作构造器
    public ProducerThread(){}

    public ProducerThread(KafkaProducer<k, v> kp, ProducerRecord<k, v> pr) {
        this.kp = kp;
        this.pr = pr;
    }

    /**
     * 多线程的核心方法，该方法可以多个线程并行运行
     */
    @Override
    public void run() {
        //run方法的代码是可以同一时刻被运行
        kp.send(pr, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                //判断是否有异常
                if(e != null){
                    System.out.println("发生非中断异常："+recordMetadata.topic());
                    e.printStackTrace(); //打印异常
                    //判断异常类型
                    if(e instanceof RetriableException){
                        System.out.println("可重试异常："+recordMetadata.topic());
                    } else {
                        System.out.println("不可重试异常："+recordMetadata.topic());
                    }
                } else {
                    //没有异常
                    System.out.println("消息发送成功："+recordMetadata.topic()
                            +"-"+recordMetadata.partition()
                            +"-"+recordMetadata.offset());
                }
            }
        });
    }
}
