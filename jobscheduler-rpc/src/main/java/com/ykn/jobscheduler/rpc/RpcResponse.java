package com.ykn.jobscheduler.rpc;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:38
 **/
@Data
@NoArgsConstructor
public class RpcResponse<T> {


    /**
     * 调用方传的请求ID
     *
     * @return
     */
    private String requestId;


    /**
     * 错误信息
     *
     * @return
     */
    private String errMsg;


    /**
     * 响应内容
     *
     * @return
     */
    private T body;

    public RpcResponse(String requestId, String errMsg, T body) {
        this.requestId = requestId;
        this.errMsg = errMsg;
        this.body = body;
    }
}
