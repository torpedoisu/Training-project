package com.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
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

public class ArticleFileDAO {

    public static Logger logger = LogManager.getLogger(ArticleFileDAO.class);        

    /**
     * FILE_TB 테이블에 file 등록하는 메서드
     * 
     * @param file - db에 등록할 파일, FileVO의 pk는 ARTICLE_TB의 외래키
     * @return ArticleFileVO
     * @throws SQLException 
     */
    public ArticleFileVO insert(DBManager dbManager, ArticleFileVO file) throws SQLException{
        logger.debug("[insert] File 등록 시작");
        
        
        PreparedStatement statement = null;
        
        String fileSql = "INSERT INTO FILE_TB (ARTICLE_PK, CONTENT) VALUES (?, ?)";

        statement = dbManager.getJdbcConnection().prepareStatement(fileSql);
        statement.setString(1, file.getExternalArticle().getPk()); // 게시글의 PK를 외래 키로 설정
        
        //BLOB 등록
        InputStream inputStream = new ByteArrayInputStream(file.getFile());
        statement.setBinaryStream(2, inputStream, file.getFile().length);
        statement.executeUpdate();
        
        logger.debug("[insert] File 등록 완료");
   
        statement.close();
        
        return file;
    }

    public List<ArticleFileVO> selectFilesByArticlePk(DBManager dbManager, String articlePk) throws SQLException{
        logger.debug("[selectFilesByArticlePk] 상세 게시글pk: " + articlePk + "에 대한 파일 조회 시작");

        List<ArticleFileVO> files = new ArrayList<ArticleFileVO>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM FILE_TB WHERE ARTICLE_PK = ?";
        
        statement = dbManager.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, articlePk);
        rs = statement.executeQuery();
        
        while (rs.next()) {
            ArticleFileVO file = new ArticleFileVO();
            file.setPk(rs.getString("PK"));
            file.setFile(rs.getBytes("CONTENT"));
            
            files.add(file);
        }

        rs.close();
        statement.close();
        
        logger.debug("[selectFilesByArticlePk] 상세 게시글pk: " + articlePk + "에 대한 파일 조회 완료");
        
        return files;
    }
}
