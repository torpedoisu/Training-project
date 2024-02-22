package com.global;

public class ResponseData {
    private Status status;
    private String statusDescription;
    private Object exceptionType;
    
    public ResponseData(Status status) {
        this.status = status;
    }
    
    public ResponseData(Status status, String statusDescription) {
        this.status = status;
        this.statusDescription = statusDescription;
    }
    
    public ResponseData(Status status, String statusDescription, Object exceptionType) {
        this.status = status;
        this.statusDescription = statusDescription;
        this.exceptionType = exceptionType;
    }
    
    public String getJsonResponseData() {
        return "{\"status\":\"" + this.status + "\","
                + "\"statusDescription\":\"" + this.statusDescription + "\"}";
    }
    
    public boolean isSuccess() {
        return this.status == Status.SUCCESS;
    }
}
