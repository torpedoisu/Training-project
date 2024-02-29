package com.vo;

import java.math.BigInteger;
import java.sql.Blob;

import com.global.UUIDFactory;

public class ArticleFileVO {
    private String uuid = null;
    private String title = null;
    private byte[] file = null;
    private ArticleVO externalArticle = null;
    
    public static ArticleFileVO getNewInstanceWithUUID() {
        ArticleFileVO articleFileVo = new ArticleFileVO();
        articleFileVo.setUUID(UUIDFactory.generateUUID().toString());
        
        return articleFileVo;
    }
    
    public String getUUID() {
        return uuid;
    }
    
    public void setUUID(String val) {
        this.uuid = val;
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
    public void setTitle(String val) {
        this.title = val;
        
    }
}
