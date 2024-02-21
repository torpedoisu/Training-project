package com.vo;

import java.math.BigInteger;
import java.sql.Blob;

public class ArticleFileVO {
    private BigInteger pk;
    private Blob file;
    
    public String getPk() {
        return String.valueOf(pk);
    }
    
    public void setPk(String pk) {
        this.pk = new BigInteger(pk);
    }
    
    public Blob getFile() {
        return file;
    }
    
    public void setFile(Blob file) {
        this.file = file;
    }
}
