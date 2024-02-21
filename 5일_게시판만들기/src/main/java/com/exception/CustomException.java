package com.exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CustomException extends RuntimeException{
    
    public static Logger logger = LogManager.getLogger(CustomException.class);
    
    private Integer code;
    
    public CustomException(String message) {
        super(message);
        logger.debug(message);
    }
    
    public CustomException(String message, Integer code) {
        super(message);
        this.code = code;
        logger.error(message);
    }

    public Integer getCode() {
        return code;
    }

}
