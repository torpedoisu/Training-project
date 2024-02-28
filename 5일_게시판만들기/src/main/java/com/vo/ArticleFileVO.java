package com.vo;

import java.math.BigInteger;
import java.sql.Blob;

public class ArticleFileVO {
    private BigInteger pk;
    private String title;
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

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
        
    }
}
