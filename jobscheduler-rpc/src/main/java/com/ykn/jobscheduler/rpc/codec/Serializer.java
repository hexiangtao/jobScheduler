package com.ykn.jobscheduler.rpc.codec;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:45
 **/
public interface Serializer {


    /**
     * 把对象序列化成字节数组
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);


    /**
     * 把字节数组反序列化成指定类型的对象
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> clazz);


}
