package com.vo;

import java.math.BigInteger;
import java.sql.Blob;

public class ArticleVO {
    
    private BigInteger pk;
    private String title;
    private Blob content;
    private ArticleFileVO file;
    
    public String getPk() {
        return String.valueOf(pk);
    }
    
    public void setPk(String pk) {
        this.pk = new BigInteger(pk);
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public ArticleFileVO getFile() {
        return file;
    }

    public void setFile(ArticleFileVO file) {
        this.file = file;
    }
    
}
