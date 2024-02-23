package com.vo;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.List;

public class ArticleVO {
    
    private BigInteger pk;
    private String title;
    private String content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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
    
    /**
     * 게시글의 제목과 본문이 존재하면 Article이 존재하는 것으로 간주
     * @return boolean
     */
    public boolean isExist() {
        if (this.title == null || this.content == null) {
            return false;
        }
        
        return true;
    }
}
