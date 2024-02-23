package com.vo;

import java.math.BigInteger;
import java.util.List;

public class UserVO {
    private BigInteger pk = null;
    private String id = null;
    private String pwd = null;
    private List<ArticleVO> externalArticles = null;
    
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
    
    public List<ArticleVO> getExternalArticles() {
        return externalArticles;
    }
    
    public void setExternalArticles(List<ArticleVO> articles) {
        this.externalArticles = articles;
    }
    
    public boolean isExist() {
        if (id == null || pwd == null) {
            return false;
        }
        return true;
    }
}
