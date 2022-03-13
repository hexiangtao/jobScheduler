package com.ykn.jobscheduler.common;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className Return
 * @date 2020/5/12 18:06
 **/
public class Return<T> {

    private int code;
    private T data;
    private String errMsg;

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int FAILED = 1;

    private Return(int code, T data) {
        this.code = code;
        this.data = data;
        this.errMsg = "";
    }

    public static <T> Return<T> create(T data, int code) {
        Return<T> response = new Return(code, data);
        return response;
    }

    public static <T> Return<T> success(T data) {
        return create(data, SUCCESS);
    }

    public static Return<String> error(String error) {
        return create(error, ERROR);
    }

    public boolean isSuccess() {
        return code == SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public T getData() {
        return data;
    }


}
