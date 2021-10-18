package org.wangtianyu.userPlatform.Model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageWrapper<T> implements Serializable {
    private String status;

    private T data;

    private String message;

    public MessageWrapper(BasicStatus status, T data, String message) {
        this.status = status.name();
        this.data = data;
        this.message = message;
    }

    public enum BasicStatus{
        SUCCESS,
        FAILED
    }
}
