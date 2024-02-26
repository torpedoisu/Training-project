package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.global.DBManager;
import com.vo.ArticleVO;
import com.vo.UserVO;

public class ArticleDAO {

    public static Logger logger = LogManager.getLogger(ArticleDAO.class);        

    /**
     * ARTICLE_TB 테이블에 게시글을 등록하는 메서드
     * 
     * @param article - db에 등록할 게시글 정보
     * @return ArticleVO
     * @throws SQLException 
     */
    public ArticleVO insert(DBManager dbManager, ArticleVO article) throws SQLException{
        logger.debug("[insert] ArticlePk: "+ article.getTitle() +" 등록 시작");
        
        PreparedStatement statement = null;
        
        String sql = "INSERT INTO ARTICLE_TB (USER_PK, TITLE, CONTENT) VALUES (?, ?, ?)";

        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, article.getExternalUser().getPk());
        statement.setString(2, article.getTitle());
        statement.setString(3, article.getContent());
        statement.executeUpdate();
        
        statement.close();
        
        logger.debug("[insert] ArticlePk: "+ article.getTitle() +" 등록 완료");
        
        return article;
    }

    /**
     * ARTICLE_TB 테이블에서 제목으로 게시글을 조회하는 메셔드
     * 
     * @param user - UserVO 객체
     * @param title - 조회에 사용될 제목
     * @return ArticleVO
     * @throws SQLException 
     */
    public ArticleVO selectWithTitle(DBManager dbManager, UserVO user, String title) throws SQLException {
        logger.debug("[selectWithTitle] user: "+ user.getId() + "의 게시글제목: " + title +" 조회 시작");

        ArticleVO article = new ArticleVO();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM ARTICLE_TB WHERE USER_PK = ? AND TITLE = ?";

        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, user.getPk());
        statement.setString(2, title);
        rs = statement.executeQuery();
        
        if (rs.next()) {
            article.setPk(rs.getString("PK"));
            article.setTitle(rs.getString("TITLE"));
            article.setContent(rs.getString("CONTENT"));
        }
        
        rs.close();
        statement.close();
            
        logger.debug("[selectWithTitle] user: "+ user.getId() + "의 게시글제목: " + article.getTitle() +" 조회 완료");
        
        return article;
    }

    /**
     * ARTICLE_TB 테이블에서 유저의 정보, 제목, 본문으로 게시글을 조회하는 메서드
     * 
     * @param article
     * @return ArticleVO
     * @throws SQLException 
     */
    public ArticleVO select(DBManager dbManager, ArticleVO article) throws SQLException {
        logger.debug("[select] user: "+ article.getExternalUser().getId() + "의 게시글제목: " + article.getTitle() +" 조회 시작");
        
        ArticleVO newArticle = new ArticleVO();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM ARTICLE_TB WHERE USER_PK = ? AND TITLE = ? AND CONTENT = ?";

        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, article.getExternalUser().getPk());
        statement.setString(2, article.getTitle());
        statement.setString(3, article.getContent());
        rs = statement.executeQuery();
        
        if (rs.next()) {
            newArticle.setPk(rs.getString("PK"));
            newArticle.setTitle(rs.getString("TITLE"));
            newArticle.setContent(rs.getString("CONTENT"));
        }
  
        rs.close();
        statement.close();
        
        logger.debug("[select] user: "+ article.getExternalUser().getId() + "의 게시글제목: " + article.getTitle() +" 조회 완료");
        
        return newArticle;
    }

    public List<ArticleVO> selectAll(DBManager dbManager) throws SQLException {
        logger.debug("[selectAll] 전체 게시글 조회 시작");
    

        List<ArticleVO> articles = new ArrayList<ArticleVO>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM ARTICLE_TB";
        
        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        rs = statement.executeQuery();
        
        while (rs.next()) {
            ArticleVO article = new ArticleVO();
            
            article.setPk(rs.getString("PK"));
            article.setTitle(rs.getString("TITLE"));
            article.setContent(rs.getString("CONTENT"));
            
            UserVO user = new UserVO();
            user.setPk(rs.getString("USER_PK"));
            article.setExternalUser(user);
            
            articles.add(article);
        }

        rs.close();
        statement.close();
        
        logger.debug("[selectAll] 전체 게시글 조회 완료");

        return articles;
    }
    
}
