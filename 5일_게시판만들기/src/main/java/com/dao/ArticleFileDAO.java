package com.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

        String fileSql = "INSERT INTO FILE_TB (UUID, ARTICLE_UUID, TITLE, CONTENT) VALUES (?, ?, ?, ?)";

        statement = dbManager.getJdbcConnection().prepareStatement(fileSql);

        statement.setString(1, file.getUUID()); // 게시글의 PK를 외래 키로 설정
        statement.setString(2, file.getExternalArticle().getUUID()); // 게시글의 PK를 외래 키로 설정
        statement.setString(3, file.getTitle());
        
        //BLOB 등록
        InputStream inputStream = new ByteArrayInputStream(file.getFile());
        statement.setBinaryStream(4, inputStream, file.getFile().length);
        
        statement.executeUpdate();

        logger.debug("[insert] File 등록 완료");
   
        statement.close();

        return file;
    }


    public void delete(DBManager dbManager, ArticleFileVO file) throws SQLException {
        logger.debug("[delete] 파일pk: " + file.getUUID() + " 삭제 시작");

        PreparedStatement pstmt = null;

        String sql = "DELETE FROM FILE_TB WHERE ARTICLE_UUID = ?";
        pstmt = dbManager.getJdbcConnection().prepareStatement(sql);
        pstmt.setString(1, file.getUUID());
        pstmt.executeUpdate();

        pstmt.close();

        logger.debug("[delete] 파일pk: " + file.getUUID() + " 삭제 완료");

    }

    public List<ArticleFileVO> selectFilesByArticlePk(DBManager dbManager, ArticleVO articleVo) throws SQLException {
        logger.debug("[selectFilesByArticlePk] articlePk: " + articleVo.getUUID() + " 의 파일 조회 시작");
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleFileVO> articleFilesVo = new ArrayList<>();

        String sql = "SELECT * FROM FILE_TB WHERE ARTICLE_UUID = ?";

        pstmt = dbManager.getJdbcConnection().prepareStatement(sql);
        pstmt.setString(1, articleVo.getUUID());
        rs = pstmt.executeQuery();

        while (rs.next()) {
            ArticleFileVO articleFileVo = new ArticleFileVO();

            articleFileVo.setFile(rs.getBytes("CONTENT"));
            articleFileVo.setUUID(rs.getString("UUID"));
            articleFileVo.setTitle(rs.getString("TITLE"));
            articleFileVo.setExternalArticle(articleVo);

            articleFilesVo.add(articleFileVo);
        }

        rs.close();
        pstmt.close();

        logger.debug("[selectFilesByArticlePk] articlePk: " + articleVo.getUUID() + " 의 파일 조회 완료");

        logger.debug("파일 " + articleFilesVo.size() + " 개 발견");
        return articleFilesVo;
    }
}
