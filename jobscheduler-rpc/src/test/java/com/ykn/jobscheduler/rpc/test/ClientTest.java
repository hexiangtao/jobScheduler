package com.ykn.jobscheduler.rpc.test;

import com.ykn.jobscheduler.rpc.RpcRequest;
import com.ykn.jobscheduler.rpc.net.Client;
import com.ykn.jobscheduler.rpc.net.impl.netty.client.NettyClient;

import java.nio.charset.StandardCharsets;

/**
 * @author hexiangtao
 * @date 2022/3/16 22:30
 **/
public class ClientTest {

    public static void main(String[] args) throws Exception {
        Client client = new NettyClient("127.0.0.1", 80);
        RpcRequest req = new RpcRequest();
        req.setBody("HELLO");
        client.send(req);
    }
}
