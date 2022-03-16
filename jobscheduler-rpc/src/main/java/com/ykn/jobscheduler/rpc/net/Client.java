package com.ykn.jobscheduler.rpc.net;

import com.ykn.jobscheduler.rpc.RpcRequest;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:35
 **/
public interface Client {


    /**
     * 发送请求
     *
     * @param request
     */
    void send(RpcRequest request);


}
