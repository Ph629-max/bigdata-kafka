package edu.nanzhi;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * 用户实体序列化
 * 1、必须实现implements Serializer<User>   ，泛型为输入的实体
 * 2、必须实现serialize(String topic, Headers headers, User data)
 */
public class UserSerializer implements Serializer<User> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, User user) {
        return new byte[0];
    }

    @Override
    public byte[] serialize(String topic, Headers headers, User data) {
        if(data == null){
            System.out.println("你输入的数据是空，请检查....");
            return null;
        }else {
            System.out.println("开始序列化...");
            //封装字节数组，并返回
            byte[] bytes = data.uname.getBytes();
            return bytes;
        }
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
