package com.global;

public class ResponseData {
    private Status status;
    private String statusDescription;
    
    public ResponseData(Status status, String statusDescription) {
        this.status = status;
        this.statusDescription = statusDescription;
    }
    
    public String getResponseData() {
        return "{\"status\":\"" + this.status + "\","
                + "\"statusDescription\":\"" + this.statusDescription + "\"}";
    }
    
    public boolean isSuccess() {
        return this.status == Status.SUCCESS;
    }
}
