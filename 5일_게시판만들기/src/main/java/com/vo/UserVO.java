package com.vo;

import java.math.BigInteger;
import java.util.List;

import com.global.UUIDFactory;

public class UserVO {
    private String uuid = null;
    private String id = null;
    private String pwd = null;
    private List<ArticleVO> externalArticles = null;
    
    public static UserVO getNewInstanceWithUUID() {
        UserVO userVo = new UserVO();
        userVo.setUUID(UUIDFactory.generateUUID().toString());
        
        return userVo;
    }
    
    public String getUUID() {
        return uuid;
    }
    public void setUUID(String val) {
        this.uuid = val;
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
    
    /**
     * 유저의 아이디와 비밀번호가 null이 아니면 유저가 존재하는 것으로 간주
     
     * @return  boolean
     */
    public boolean isExist() {
        if (id == null || pwd == null || uuid == null) {
            return false;
        }
        return true;
    }
}
