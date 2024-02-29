package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.global.DBManager;
import com.vo.ArticleFileVO;
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
        
        String sql = "INSERT INTO ARTICLE_TB (UUID, TITLE, CONTENT, USER_UUID) VALUES (?, ?, ?, ?)";

        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, article.getUUID());
        statement.setString(2, article.getTitle());
        statement.setString(3, article.getContent());
        statement.setString(4, article.getExternalUser().getUUID());
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
        
        String sql = "SELECT * FROM ARTICLE_TB WHERE USER_UUID = ? AND TITLE = ?";

        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, user.getUUID());
        statement.setString(2, title);
        rs = statement.executeQuery();
        
        if (rs.next()) {
            article.setUUID(rs.getString("UUID"));
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
        
        String sql = "SELECT * FROM ARTICLE_TB WHERE USER_UUID = ? AND TITLE = ? AND CONTENT = ?";

        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, article.getExternalUser().getUUID());
        statement.setString(2, article.getTitle());
        statement.setString(3, article.getContent());
        rs = statement.executeQuery();
        
        if (rs.next()) {
            newArticle.setUUID(rs.getString("UUID"));
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
            
            article.setUUID(rs.getString("UUID"));
            article.setTitle(rs.getString("TITLE"));
            article.setContent(rs.getString("CONTENT"));
            
            UserVO user = new UserVO();
            user.setUUID(rs.getString("USER_UUID"));
            article.setExternalUser(user);
            
            articles.add(article);
        }

        rs.close();
        statement.close();
        
        logger.debug("[selectAll] 전체 게시글 조회 완료");

        return articles;
    }

    public ArticleVO selectByPk(DBManager dbManager, String articlePk) throws SQLException{
        logger.debug("[selectByPk] 상세 게시글pk - " + articlePk + " 조회 시작");
        
        ArticleVO article = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        
        String sql = "SELECT UUID, TITLE, USER_UUID, CONTENT FROM ARTICLE_TB WHERE UUID = ?";
        pstmt = dbManager.getJdbcConnection().prepareStatement(sql);
        pstmt.setString(1, articlePk);
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            article = new ArticleVO();
            article.setUUID(rs.getString("UUID"));
            article.setTitle(rs.getString("TITLE"));
            article.setContent(rs.getString("CONTENT"));
            
            // 유저 정보 세팅
            UserVO userVo = new UserVO();
            userVo.setUUID(rs.getString("USER_UUID"));
            article.setExternalUser(userVo);
        }
        
        logger.debug("[selectByPk] 상세 게시글pk - " + articlePk + " 조회 완료");
        return article;
    }

    public void delete(DBManager dbManager, ArticleVO article) throws SQLException{
        logger.debug("[delete] 상세 게시글pk - " + article.getUUID() + " 삭제 시작");
        
        PreparedStatement pstmt = null;
        
        String sql = "DELETE FROM ARTICLE_TB WHERE UUID = ?";
        pstmt = dbManager.getJdbcConnection().prepareStatement(sql);
        pstmt.setString(1, article.getUUID());
        pstmt.executeUpdate();
        
        pstmt.close();
        
        logger.debug("[delete] 상세 게시글 - " + article.getUUID() + " 삭제 완료");
    }

    public void update(DBManager dbManager, ArticleVO article) throws SQLException{
        logger.debug("[update] 상세 게시글pk - " + article.getUUID() + " 업데이트 시작");
        
        PreparedStatement pstmt = null;
        
        String sql = "UPDATE ARTICLE_TB SET TITLE = ?, CONTENT = ? WHERE UUID = ?";
        pstmt = dbManager.getJdbcConnection().prepareStatement(sql);
        pstmt.setString(1, article.getTitle());
        pstmt.setString(2, article.getContent());
        pstmt.setString(3, article.getUUID());
        
        pstmt.executeUpdate();
        
        pstmt.close();
        
        logger.debug("[update] 상세 게시글 - " + article.getUUID() + " 업데이트 완료");
        
    }
    
}
