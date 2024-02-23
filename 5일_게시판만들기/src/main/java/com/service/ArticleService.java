package com.service;

import java.sql.Blob;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dao.ArticleDAO;
import com.dao.ArticleFileDAO;
import com.exception.CustomException;
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
        logger.debug("Service - 게시글 등록 시작");

        content = (content == null) ? "" : content;
        title = (title == null) ? "" : content;
        
        ArticleVO article = new ArticleVO();
        article.setExternalUser(user);
        article.setTitle(title);
        article.setContent(content);

        // 유저는 같은 제목을 등록할 수 없음
        ArticleDAO articleDao = new ArticleDAO();
        ArticleVO articleInDB = articleDao.selectWithTitle(user, title);
        
        if (articleInDB.isExist()) {
            throw new CustomException("이미 등록한 제목입니다. 다른 제목을 사용해주세요", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
        }
        
        // 게시글 등록
        articleDao.insert(article);
        
        // 게시글의 fk를 위한 게시글 pk 조회
        ArticleVO articleInDBWithPK = articleDao.select(article);
        if (!articleInDBWithPK.isExist()) {
            throw new CustomException("Service - db에 게시글 등록 후 pk 가져오던 중 오류", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
        }
        article.setPk(articleInDBWithPK.getPk());
          
        // 파일 등록
        ArticleFileVO articleFileVo = new ArticleFileVO();
        ArticleFileDAO articleFileDao = new ArticleFileDAO();
        for (byte[] file: files) {
            articleFileVo.setExternalArticle(article);
            articleFileVo.setFile(file);
            
            // 파일 등록
            articleFileDao.insert(articleFileVo);
        }
        
        
        logger.debug("Service - 게시글 등록 완료");
    }
}
