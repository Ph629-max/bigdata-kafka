package edu.nanzhi.IDIdenfier;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * 自定义申请实体的序列化器
 */
public class ApplicationInfoSerilizer implements Serializer<ApplicationInfo> {
    private String encoding = "UTF-8";  //定义编码格式
    private ObjectMapper objectMapper = new ObjectMapper();  //定义对象映射实例
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String encodingValue = isKey ? "key.serializer.encoding" : "key.serializer.encoding";
        Object oe = configs.get(encodingValue);  //从配置中获取key的序列化方式
        //判断序列化方式
        if(oe == null){
            oe = configs.get("serializer.encoding");
        }
        //再判断是否获取
        if(oe != null && oe instanceof String){
          encoding =  (String) oe;
        }
    }

    @Override
    public byte[] serialize(String s, ApplicationInfo applicationInfo) {
        byte[] res = null;
        try{
            //将对象写成String，并获取该String字节数组
            res = objectMapper.writeValueAsString(applicationInfo).getBytes(encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void close() {
        //do nothing
    }
}
