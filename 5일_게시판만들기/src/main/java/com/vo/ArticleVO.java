package com.vo;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.List;

public class ArticleVO {
    
    private BigInteger pk;
    private String title;
    private Blob content;
    private List<ArticleFileVO> externalFiles;
    private UserVO externalUser;
    
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

    public List<ArticleFileVO> getExternalFiles() {
        return externalFiles;
    }

    public void setExternalFiles(List<ArticleFileVO> files) {
        this.externalFiles = files;
    }
    
    public UserVO getExternalUser() {
        return externalUser;
    }
    
    public void setExternalUser(UserVO user) {
        this.externalUser = user;
    }
}
