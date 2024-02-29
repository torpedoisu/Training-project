package com.vo;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.List;

import com.global.UUIDFactory;

public class ArticleVO {
    
    private String uuid = null;
    private String title = null;
    private String content = null;
    private List<ArticleFileVO> externalFiles = null;
    private UserVO externalUser = null;
    
    public static ArticleVO getNewInstanceWithUUID() {
        ArticleVO articleVo = new ArticleVO();
        articleVo.setUUID(UUIDFactory.generateUUID().toString());
        
        return articleVo;
    }
    
    public String getUUID() {
        return uuid;
    }
    
    public void setUUID(String val) {
        this.uuid = val;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String val) {
        this.title = val;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String val) {
        this.content = val;
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
        if (this.title == null || this.content == null || this.uuid == null) {
            return false;
        }
        
        return true;
    }
}
