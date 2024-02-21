package com.vo;

import java.math.BigInteger;

public class UserVO {
    private BigInteger pk;
    private String id;
    private String pwd;
    
    public String getPk() {
        return String.valueOf(pk);
    }
    public void setPk(String val) {
        this.pk = new BigInteger(val);
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String val) {
        this.id = val;
    }
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String val) {
        this.pwd = val;
    }
}
