package com.exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class UserException extends RuntimeException{
    
    public static Logger logger = LogManager.getLogger(UserException.class);
    
    private Integer code;
    
    public UserException(String message) {
        super(message);
        logger.debug(message);
    }
    
    public UserException(String message, Integer code) {
        super(message);
        this.code = code;
        logger.error(message);
    }

    public Integer getCode() {
        return code;
    }

}
