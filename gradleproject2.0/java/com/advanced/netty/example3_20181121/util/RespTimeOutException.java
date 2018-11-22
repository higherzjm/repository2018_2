package com.advanced.netty.example3_20181121.util;

/**
 * 请求异常
 *
 * @author dengbin
 * @date 2016/10/9
 */
public class RespTimeOutException extends Exception {
    private String msg;
    public RespTimeOutException(){
        super();
    }
    public RespTimeOutException(String msg){
        super();
        this.msg=msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
