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
    public void registerArticle(ArticleVO articleVo) {
        logger.debug("Service - 게시글 등록 트랜잭션 시작");
        
        DBManager dbManager = new DBManager();
        
        dbManager.connect();
        try {
            if (articleVo.getContent().trim().isEmpty()) {
                throw new CustomException("본문을 입력해주세요", HttpServletResponse.SC_BAD_REQUEST, "editArticle.jsp");
            }
            if (articleVo.getTitle().trim().isEmpty()) {
                throw new CustomException("제목을 입력해주세요", HttpServletResponse.SC_BAD_REQUEST, "editArticle.jsp");
            }   
            // 유저는 같은 제목을 등록할 수 없음
            ArticleDAO articleDao = new ArticleDAO();
            ArticleVO articleInDB = articleDao.selectWithTitle(dbManager, articleVo.getExternalUser(), articleVo.getTitle());
            
            if (articleInDB.isExist()) {
                throw new CustomException("이미 등록한 제목입니다. 다른 제목을 사용해주세요", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
            }
            
            // 게시글 등록
            articleDao.insert(dbManager, articleVo);
            
            // 게시글의 fk를 위한 게시글 pk 조회
            ArticleVO articleInDBWithPK = articleDao.select(dbManager, articleVo);
            if (!articleInDBWithPK.isExist()) {
                throw new CustomException("Service - db에 게시글 등록 후 pk 가져오던 중 오류", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
            }
            articleVo.setPk(articleInDBWithPK.getPk());
              
            // 파일 등록
            List<ArticleFileVO> articleFilesVo = articleVo.getExternalFiles();
            ArticleFileDAO articleFileDao = new ArticleFileDAO();
            int index = 1;
            for (ArticleFileVO articleFileVo : articleFilesVo) {
                articleFileDao.insert(dbManager, articleFileVo);
                logger.debug(index++ + "번째 파일 등록 완료");
            }
            
            dbManager.commit();
            
            logger.debug("Service - 게시글 트랜잭션 완료");
            
        } catch (SQLException e) {
            logger.error("게시물 등록 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
        
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
            
            logger.debug("Service - 전체 게시글 조회 완료");
            
        } catch (SQLException e) {
            logger.error("전체 게시물 조회 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
        
        return articles;
        
    }

    public ArticleVO getArticle(String articlePk) {
        logger.debug("Service - 게시물 상세 조회 트랜잭션 시작");
        DBManager dbManager = new DBManager();
        
        dbManager.connect();
        
        ArticleVO article = null;
        try {
            ArticleDAO articleDao = new ArticleDAO();
            article = articleDao.selectByPk(dbManager, articlePk);
            
            if (article == null) {
                throw new CustomException("게시글 조회가 실패했습니다", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "index.jsp");
            }
            
            // 게시글의 작성자 조회
            UserDAO userDao = new UserDAO();
            String userPk = article.getExternalUser().getPk();
                
            UserVO user = userDao.selectUserWithPk(dbManager, userPk);
            article.setExternalUser(user);
            
            // 파일 불러오기
            ArticleFileDAO articleFileDao = new ArticleFileDAO();

            List<ArticleFileVO> articleFilesVo = articleFileDao.selectFilesByArticlePk(dbManager, article);
            if (articleFilesVo.size() != 0 && articleFilesVo != null) {
                article.setExternalFiles(articleFilesVo);
                // 파일에도 게시글 넣어주기 (양방향
                for (ArticleFileVO articleFile : articleFilesVo) {
                    articleFile.setExternalArticle(article);
                }
            }
            
            dbManager.commit();
            
            logger.debug("Service - 게시글 상세 조회 트랜잭션 완료");
            
        } catch (SQLException e) {
            logger.error("게시물 상세 조회 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
        
        
        
        return article;
        
    }

    public void delete(String articlePk) {
        logger.debug("Service - 게시글 삭제 트랜잭션 시작");
        
        DBManager dbManager = new DBManager();
        
        dbManager.connect();
        
        ArticleVO article = null;
        try {
            ArticleDAO articleDao = new ArticleDAO();
            article = articleDao.selectByPk(dbManager, articlePk);
            
            if (article == null) {
                throw new CustomException("이미 삭제된 게시글입니다", HttpServletResponse.SC_BAD_REQUEST, "index.jsp");
            }
            
            // 게시글과 연결된 파일이 있는지도 확인
            ArticleFileDAO articleFileDao = new ArticleFileDAO();
            List<ArticleFileVO> articleFilesVo = articleFileDao.selectFilesByArticlePk(dbManager, article);
            if (articleFilesVo.size() != 0) {
                for (ArticleFileVO file : articleFilesVo) {
                    articleFileDao.delete(dbManager, file);
                }
            }
            
            articleDao.delete(dbManager, article);
            
            dbManager.commit();
            
            logger.debug("Service - 게시글 삭제 트랜잭션 완료");
        } catch (SQLException e) {
            logger.error("게시글 삭제 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();
            }
        }
        
    }

    public void editArticle(ArticleVO articleVo) {
        logger.debug("Service - 게시글 수정 트랜잭션 시작");
        
        DBManager dbManager = new DBManager();
        
        dbManager.connect();
        try {
            if (articleVo.getContent().trim().isEmpty()) {
                throw new CustomException("본문을 입력해주세요", HttpServletResponse.SC_BAD_REQUEST, "editArticle.jsp?pk=" + articleVo.getPk());
            }
            if (articleVo.getTitle().trim().isEmpty()) {
                throw new CustomException("제목을 입력해주세요", HttpServletResponse.SC_BAD_REQUEST, "editArticle.jsp?pk=" + articleVo.getPk());
            }   
            
            
            
            /* 파일은 여러개일 수 있고 같은 제목에 내용이 바뀔 수 있기 때문에 
               article_pk로 조회해서 해당되는 파일은 삭제하고 나머지 insert
            */
            
            ArticleFileDAO articleFileDao = new ArticleFileDAO();

            // 1. 기존의 파일 삭제
            List<ArticleFileVO> earlierFiles = articleFileDao.selectFilesByArticlePk(dbManager, articleVo);
            for (ArticleFileVO earlierfile : earlierFiles) {
                articleFileDao.delete(dbManager, earlierfile);
            }
            
            // 2. article은 중복되는 경우가 없기 때문에 바로 업데이트
            ArticleDAO articleDao = new ArticleDAO();
            articleDao.update(dbManager, articleVo);
            
            // 3. 파일 저장을 위한 article pk 조회 후 저장 
            ArticleVO newArticle = articleDao.select(dbManager, articleVo);
            
            // 4. 유저가 새로 등록한 파일 저장
            List<ArticleFileVO> articleFilesVo = articleVo.getExternalFiles();
            for (ArticleFileVO articleFile : articleFilesVo) {
                articleFileDao.insert(dbManager, articleFile);
            }
            
            dbManager.commit();
            
            logger.debug("Service - 게시글 트랜잭션 완료");
            
        } catch (SQLException e) {
            logger.error("게시물 등록 중 예외 발생");
            e.printStackTrace();
            dbManager.rollback();
        } finally {
            if (!dbManager.checkJdbcConnectionIsClosed()) {
                dbManager.disconnect();    
            }
        }
    }
    
}
