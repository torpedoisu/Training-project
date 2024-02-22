package com.exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CustomException extends RuntimeException{
    
    public static Logger logger = LogManager.getLogger(CustomException.class);
    
    private Integer httpStatusCode;
    private String nextPath;
    
    public CustomException(String message) {
        super(message);
        logger.error(message);
    }
    
    public CustomException(String message, Integer httpStatusCode, String nextPath) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.nextPath = nextPath;
        logger.error(message);
    }

    public Integer getCode() {
        return httpStatusCode;
    }

    public String getNextPath() {
        return nextPath;
    }
}
