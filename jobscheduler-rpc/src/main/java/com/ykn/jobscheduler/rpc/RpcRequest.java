package com.ykn.jobscheduler.rpc;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:36
 **/
@Data
@NoArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 请求唯一标识
     *
     * @return
     */
    private String requestId;


    /**
     * 请求时间
     *
     * @return
     */
    private long timestamp;


    /**
     * 请求token
     *
     * @return
     */
    private String accessToken;

    /**
     * 请求体
     *
     * @return
     */
    private byte[] body;


    public RpcRequest(String requestId, long timestamp, String accessToken, byte[] body) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.accessToken = accessToken;
        this.body = body;
    }
}
