package com.dao;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.CustomException;
import com.global.DBManager;
import com.vo.ArticleFileVO;

public class ArticleFileDAO {
    private DBManager dbManager = null;

    public static Logger logger = LogManager.getLogger(UserDAO.class);        

    public ArticleFileDAO() {
        dbManager = new DBManager();
    }

    /**
     * FILE_TB 테이블에 file 등록하는 메서드
     * 
     * @param file - db에 등록할 파일, FileVO의 pk는 ARTICLE_TB의 외래키
     * @return ArticleFileVO
     */
    public ArticleFileVO insert(ArticleFileVO file){
        logger.debug("FilePk: "+ file.getPk() +" 등록 시작");
        
        dbManager.connect();
        
        PreparedStatement statement = null;

        String fileSql = "INSERT INTO file_tb (article_pk, file) VALUES (?, ?)";
        
        try {
            statement = dbManager.getJdbcConnection().prepareStatement(fileSql);
            statement.setString(1, file.getPk()); // 게시글의 PK를 외래 키로 설정
            statement.setBlob(2, file.getFile());
            statement.executeUpdate();
            
            dbManager.commit(); 
            
            logger.debug("file: " + " 등록 완료");
        } catch (SQLException e) {
            e.printStackTrace();
            dbManager.rollback();
            throw new CustomException(("파일을 DB에 insert 도중 에러 - " + e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect(statement);    
            }
        }
        
        return file;
    }
}
