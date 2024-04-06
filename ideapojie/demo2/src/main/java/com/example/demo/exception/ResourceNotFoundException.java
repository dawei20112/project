package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)//设置请求条件

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
    }
//如果找不到对应的信息返回一个异常
    public ResourceNotFoundException(String message) {
        super(message);
    }  //调用这个方法复写错误信息

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
