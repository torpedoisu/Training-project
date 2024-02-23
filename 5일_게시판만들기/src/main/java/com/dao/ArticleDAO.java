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
    private DBManager dbManager = null;

    public static Logger logger = LogManager.getLogger(ArticleDAO.class);        

    public ArticleDAO() {
        dbManager = new DBManager();
    }

    /**
     * ARTICLE_TB 테이블에 게시글을 등록하는 메서드
     * 
     * @param article - db에 등록할 게시글 정보
     * @return ArticleVO
     */
    public ArticleVO insert(ArticleVO article){
        logger.debug("[insert] ArticlePk: "+ article.getTitle() +" 등록 시작");
        
        dbManager.connect();
        
        PreparedStatement statement = null;
        
        String sql = "INSERT INTO ARTICLE_TB (USER_PK, TITLE, CONTENT) VALUES (?, ?, ?)";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, article.getExternalUser().getPk());
            statement.setString(2, article.getTitle());
            statement.setString(3, article.getContent());
            statement.executeUpdate();
            
            dbManager.commit(); 
            
            logger.debug("[insert] ArticlePk: "+ article.getTitle() +" 등록 완료");
            
        } catch (SQLException e) {
            logger.error("게시글을 DB에 insert 도중 에러");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement);    
            }
        }
        
        return article;
        
    }

    /**
     * ARTICLE_TB 테이블에서 제목으로 게시글을 조회하는 메셔드
     * 
     * @param user - UserVO 객체
     * @param title - 조회에 사용될 제목
     * @return ArticleVO
     */
    public ArticleVO selectWithTitle(UserVO user, String title) {
        logger.debug("[selectWithTitle] user: "+ user.getId() + "의 게시글제목: " + title +" 조회 시작");
        
        dbManager.connect();
        
        ArticleVO article = new ArticleVO();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM ARTICLE_TB WHERE USER_PK = ? AND TITLE = ?";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, user.getPk());
            statement.setString(2, title);
            rs = statement.executeQuery();
            
            if (rs.next()) {
                article.setPk(rs.getString("PK"));
                article.setTitle(rs.getString("TITLE"));
                article.setContent(rs.getString("CONTENT"));
            }
            
            
            logger.debug("[selectWithTitle] user: "+ user.getId() + "의 게시글제목: " + article.getTitle() +" 조회 완료");
            
        } catch (SQLException e) {
            logger.error("게시글을 DB에 select 도중 에러");
            e.printStackTrace();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement, rs);    
            }
        }
        
        return article;
    }

    /**
     * ARTICLE_TB 테이블에서 유저의 정보, 제목, 본문으로 게시글을 조회하는 메서드
     * 
     * @param article
     * @return ArticleVO
     */
    public ArticleVO select(ArticleVO article) {
        logger.debug("[select] user: "+ article.getExternalUser().getId() + "의 게시글제목: " + article.getTitle() +" 조회 시작");
        
        dbManager.connect();
        
        ArticleVO newArticle = new ArticleVO();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM ARTICLE_TB WHERE USER_PK = ? AND TITLE = ? AND CONTENT = ?";
        
        try {
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

            logger.debug("[select] user: "+ article.getExternalUser().getId() + "의 게시글제목: " + article.getTitle() +" 조회 완료");
            
        } catch (SQLException e) {
            logger.error("게시글을 DB에 select 도중 에러");
            e.printStackTrace();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement, rs);    
            }
        }
        
        return newArticle;
    }

    public List<ArticleVO> selectAll() {
        logger.debug("[selectAll] 전체 게시글 조회 시작");
        
        dbManager.connect();

        List<ArticleVO> articles = new ArrayList<ArticleVO>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM ARTICLE_TB";
        
        try {
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

            logger.debug("[selectAll] 전체 게시글 조회 완료");
            
        } catch (SQLException e) {
            logger.error("게시글을 DB에 select 도중 에러");
            e.printStackTrace();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement, rs);    
            }
        }
        
        return articles;
    }
    
}
