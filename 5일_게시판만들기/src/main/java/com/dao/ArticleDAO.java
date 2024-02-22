package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.CustomException;
import com.global.DBManager;
import com.vo.ArticleVO;
import com.vo.UserVO;

public class ArticleDAO {
    private DBManager dbManager = null;

    public static Logger logger = LogManager.getLogger(UserDAO.class);        

    public ArticleDAO() {
        dbManager = new DBManager();
    }

    /**
     * ARTICLE_TB 테이블에 게시글을 등록하는 메서드
     * 
     * @param article - db에 등록할 게시글 정보, pk는 USER_TB의 외래키
     * @return ArticleVO
     */
    public ArticleVO insert(ArticleVO article){
        logger.debug("ArticlePk: "+ article.getPk() +" 등록 시작");
        
        dbManager.connect();
        
        PreparedStatement statement = null;
        
        String sql = "INSERT INTO article_tb (pk, title, content) VALUES (?, ?, ?)";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(sql);
            statement.setString(1, article.getPk());
            statement.setString(2, article.getTitle());
            statement.setBlob(3, article.getContent());
            statement.executeUpdate();
            
            
            dbManager.commit(); 
            
            logger.debug("ArticlePk: "+ article.getPk() +" 등록 완료");
            
        } catch (SQLException e) {
            logger.error("게시글을 DB에 insert 도중 에러");
            e.printStackTrace();
            dbManager.rollback();
            } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement);    
            }
            
            return article;
        }
        
    }
    
}
