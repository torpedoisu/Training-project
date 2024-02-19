package com.exception;

public class LoginException extends RuntimeException{
    private Integer code;
    private Object type;
    private String uri;
    
    public LoginException(String message) {
        super(message);
    }
    
    public LoginException(String message, Integer code, Object type, String uri) {
        super(message);
        this.code = code;
        this.type = type;
        this.uri = uri;
    }

    public Integer getCode() {
        return code;
    }

    public Object getType() {
        return type;
    }
}
