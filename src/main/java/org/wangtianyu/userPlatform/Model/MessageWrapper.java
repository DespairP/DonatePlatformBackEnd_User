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

    public static <U> MessageWrapper<U> createUnauthorizedMessage(){
        return new MessageWrapper<U>(BasicStatus.FAILED,null,"authentication required");
    }

    public enum BasicStatus{
        SUCCESS,
        FAILED,
        DENY
    }
}
