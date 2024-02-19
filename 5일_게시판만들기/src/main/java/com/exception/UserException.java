package com.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserException extends RuntimeException{
    
    public static Logger logger = LogManager.getLogger("UserException.class");
    
    private Integer code;
    private Object type;
    
    
    public UserException(String message) {
        super(message);
        logger.debug(message);
    }
    
    public UserException(String message, Integer code, Object type) {
        super(message);
        this.code = code;
        this.type = type;
        logger.debug("=== [ERROR] " + type + " - "+ message + " ===");
    }

    public Integer getCode() {
        return code;
    }

    public Object getType() {
        return type;
    }
}
