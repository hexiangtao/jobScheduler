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
public class RpcRequest<T> implements Serializable {

    public static final String HEADER_NAME_REQUEST_ID = "x-requestId";
    public static final String HEADER_TIMESTAMP = "x-timestamp";
    public static final String HEADER_ACCESS_TOKEN = "x-accessToken";


    private String requestUri;

    private String httpMethod;

    private boolean keepAlive;

    /**
     * 请求唯一标识
     */
    private String requestId;


    /**
     * 请求时间
     */
    private Long timestamp;


    /**
     * 请求token
     */
    private String accessToken;

    /**
     * 请求体
     */
    private T body;

    public RpcRequest(String requestUri, String httpMethod, boolean keepAlive, String requestId, Long timestamp, String accessToken) {
        this.requestUri = requestUri;
        this.httpMethod = httpMethod;
        this.keepAlive = keepAlive;
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.accessToken = accessToken;
    }
}
