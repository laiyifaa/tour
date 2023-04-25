package com.ischen.entity;

import java.io.Serializable;

/**
 * Result
 *
 * @Author: ischen
 * @CreateTime: 2021-06-29
 * @Description:
 *
 * Result:客户端请求服务器，服务器返回给客户端的数据
 * 普通结果集
 */
public class Result implements Serializable {
    // 返回执行结果 标记：true：表示服务器响应成功，false：表示服务器响应失败
    private boolean flag;
    // 返回结果信息 比如：查询成功，删除失败
    private String message;
    // 返回结果集 findById ---> 返回对象，findAll--->查询所有
    private Object data;

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}