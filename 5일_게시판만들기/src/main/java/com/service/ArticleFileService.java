package com.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dao.ArticleFileDAO;
import com.global.DBManager;
import com.vo.ArticleFileVO;
import com.vo.ArticleVO;

public class ArticleFileService {
    
    public static Logger logger = LogManager.getLogger(ArticleFileService.class);
    
    public static ArticleFileService articleFileService = null;
    
    private ArticleFileService() {}
    
    public static ArticleFileService getInstance() {
        if (articleFileService == null) {
            synchronized(ArticleFileService.class) {
                articleFileService = new ArticleFileService();
            }
        } 
        return articleFileService;
    }
    
    public List<ArticleFileVO> getFiles(String articlePk) {
        logger.debug("Service - 파일 조회 트랜잭션 시작");
        
        DBManager dbManager = new DBManager();
        
        ArticleVO articleVo = new ArticleVO();
        articleVo.setPk(articlePk);
        
        List<ArticleFileVO> articleFiles = null;
        try {
            dbManager.connect();
            
            ArticleFileDAO articleFileDao = new ArticleFileDAO();
            articleFiles = articleFileDao.selectFilesByArticlePk(dbManager, articleVo);
            
            dbManager.commit();
            
            logger.debug("Service - 파일 조회 트랜잭션 완료");
        } catch (SQLException e) {
            logger.debug("파일 불러오기 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();
            }
        }
        
        return articleFiles;
    }
    
    
}
