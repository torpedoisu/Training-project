package com.vo;

import java.math.BigInteger;

public class UserVO {
    private BigInteger pk;
    private String id;
    private String pwd;
    
    public BigInteger getPk() {
        return pk;
    }
    
    public String getId() {
        return id;
    }
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPk(String val) {
        this.pk = new BigInteger(val);
    }
    
    public void setId(String val) {
        this.id = val;
    }
    
    public void setPwd(String val) {
        this.pwd = val;
    }
}
