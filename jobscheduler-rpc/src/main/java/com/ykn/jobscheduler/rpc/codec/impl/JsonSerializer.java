package com.ykn.jobscheduler.rpc.codec.impl;

import com.alibaba.fastjson.JSON;
import com.ykn.jobscheduler.rpc.codec.Serializer;

/**
 * @author hexiangtao
 * @date 2022/3/15 22:16
 **/
public class JsonSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        System.out.println(new String(data));
        return JSON.parseObject(data, clazz);
    }
}
