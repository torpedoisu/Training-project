package com.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dao.ArticleDAO;
import com.dao.ArticleFileDAO;
import com.dao.UserDAO;
import com.exception.CustomException;
import com.global.DBManager;
import com.vo.ArticleFileVO;
import com.vo.ArticleVO;
import com.vo.UserVO;

public class ArticleService {

    public static Logger logger = LogManager.getLogger(ArticleService.class);
    
    private static ArticleService articleService = null;
    
    private ArticleService() {}
    
    public static ArticleService getInstance() {
        if (articleService == null) {
            synchronized (ArticleService.class) {
                articleService = new ArticleService();    
            }
        }
        return articleService;
    }
    
    /**
     * 게시글을 등록하는 메서드
     * 
     * 예외 처리
     * - 중복된 제목인지 체크 (db에서 auto increment 사용했기 때문)
     * 
     * @param user - 세션에서 추출한 user객체
     * @param title - 게시글 제목
     * @param content - 게시글 본문
     * @param files - 파일이 저장된 List
     */
    public void registerArticle(UserVO user, String title, String content, List<byte[]> files) {
        logger.debug("Service - 게시글 등록 트랜잭션 시작");
        
        DBManager dbManager = new DBManager();
        
        dbManager.connect();
        try {
            content = (content == null) ? "" : content;
            title = (title == null) ? "" : content;
            
            ArticleVO article = new ArticleVO();
            article.setExternalUser(user);
            article.setTitle(title);
            article.setContent(content);
    
            // 유저는 같은 제목을 등록할 수 없음
            ArticleDAO articleDao = new ArticleDAO();
            ArticleVO articleInDB = articleDao.selectWithTitle(dbManager, user, title);
            
            if (articleInDB.isExist()) {
                throw new CustomException("이미 등록한 제목입니다. 다른 제목을 사용해주세요", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
            }
            
            // 게시글 등록
            articleDao.insert(dbManager, article);
            
            // 게시글의 fk를 위한 게시글 pk 조회
            ArticleVO articleInDBWithPK = articleDao.select(dbManager, article);
            if (!articleInDBWithPK.isExist()) {
                throw new CustomException("Service - db에 게시글 등록 후 pk 가져오던 중 오류", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
            }
            article.setPk(articleInDBWithPK.getPk());
              
            // 파일 등록
            ArticleFileVO articleFileVo = new ArticleFileVO();
            ArticleFileDAO articleFileDao = new ArticleFileDAO();
            int index = 1;
            for (byte[] file: files) {
                articleFileVo.setExternalArticle(article);
                articleFileVo.setFile(file);
                
                // 파일 등록
                articleFileDao.insert(dbManager, articleFileVo);
                logger.debug(index++ + "번째 파일 등록 완료");
            }
            
            dbManager.commit();
            
        } catch (SQLException e) {
            logger.error("게시물 등록 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
        
        logger.debug("Service - 게시글 트랜잭션 완료");
    }

    /** 
     * 모든 게시글을 조회하는 메서드
     * 
     * 예외 처리
     * - 게시글들을 db에서 받아오지 못하는 경우 체크
     * - 게시글의 작성자 정보를 받아오지 못하는 경우 체크
     * @return List<ArticleVO>
     */
    public List<ArticleVO> getArticles() {
        logger.debug("Service - 전체 게시물 조회 트랜잭션 시작");
        DBManager dbManager = new DBManager();
        
        dbManager.connect();
        
        List<ArticleVO> articles = new ArrayList();
        try {
            ArticleDAO articleDao = new ArticleDAO();
            
            // 게시글 조회 완료
            articles = articleDao.selectAll(dbManager);
            if (articles.isEmpty()) {
                throw new CustomException("게시글 정보들을 찾을 수 없습니다", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "login.jsp");
            }
            
            // 게시글의 작성자 조회
            UserDAO userDao = new UserDAO();
            for (ArticleVO article : articles) {
                String userPk = article.getExternalUser().getPk();
                
                if (userPk == null) {
                    throw new CustomException("ARTICLE_TB에서 user fk를 조회할 수 없습니다");
                }
                
                UserVO user = userDao.selectUserWithPk(dbManager, userPk);
                article.setExternalUser(user);
            }
            
            dbManager.commit();
            
        } catch (SQLException e) {
            logger.error("전체 게시물 조회 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
        
        logger.debug("Service - 전체 게시글 조회 완료");
        
        return articles;
        
    }
}
