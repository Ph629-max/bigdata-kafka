package edu.nanzhi;

import edu.nanzhi.util.KafkaHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 多个线程共用一个KafkaProducer实例测试
 */
public class MutiProducerTest {
    //定义KafkaProducer生产者
    private static KafkaProducer<String,String> kafkaProducer;
    public static void main(String[] args) {
        //为KafkaProducer赋值或者实例化
        Properties prop = new KafkaHelper().initConf();
        kafkaProducer = new KafkaProducer<>(prop);
        //创建大小为5的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //向kafka以多线程方式发送数据
        try {
            //循环发送8条数据
            for (int i = 0;i<8;i++){
                //构建一个生产记录
                ProducerRecord<String, String> record = new ProducerRecord<>("muti-thread-topic", "thread_" + i);
                //使用线程池服务提交生产
                Future<?> future = executorService.submit(new ProducerThread<String, String>(MutiProducerTest.kafkaProducer, record));
                //获取future的返回情况
                try {
                    future.get();
                } catch (InterruptedException e){
                    e.printStackTrace();
                } catch (ExecutionException e){
                    e.printStackTrace();
                }
            }
        } finally {
            //关闭生产者对象
            kafkaProducer.close();
            //关闭线程池
            executorService.shutdown();
        }
    }
}
