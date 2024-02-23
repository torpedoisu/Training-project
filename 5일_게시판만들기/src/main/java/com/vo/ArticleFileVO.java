package com.vo;

import java.math.BigInteger;
import java.sql.Blob;

public class ArticleFileVO {
    private BigInteger pk;
    private byte[] file;
    private ArticleVO externalArticle;
    
    public String getPk() {
        return String.valueOf(pk);
    }
    
    public void setPk(String pk) {
        this.pk = new BigInteger(pk);
    }
    
    public byte[] getFile() {
        return file;
    }
    
    public void setFile(byte[] file) {
        this.file = file;
    }
    
    public ArticleVO getExternalArticle() {
        return externalArticle;
    }
    
    public void setExternalArticle(ArticleVO article) {
        this.externalArticle = article;
    }
}
