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
    
    public void registerArticle(UserVO user, String title, String content, List<byte[]> files) {
        
        logger.debug("Service - 게시글 등록 시작");
        
        boolean userIsValid = UserService.getInstance().checkUserIsValidInSession(user.getPk(), user.getId(), user.getPwd());
        
        if (!userIsValid) {
            throw new CustomException("세션이 잘못되었습니다 다시 로그인해주세요.", HttpServletResponse.SC_FORBIDDEN, "login.jsp");
        }

        content = (content == null) ? "" : content;
        title = (title == null) ? "" : content;
        
        ArticleVO article = new ArticleVO();
        article.setExternalUser(user);
        article.setTitle(title);
        article.setContent(content);

//      유저는 같은 제목을 등록할 수 없음
        ArticleDAO articleDao = new ArticleDAO();
        ArticleVO articleInDB = articleDao.selectWithTitle(user, title);
        
        if (articleInDB.isExist()) {
            throw new CustomException("이미 등록한 제목입니다. 다른 제목을 사용해주세요", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
        }
        
//      게시글 등록
        ArticleVO  insertedArticle = articleDao.insert(article);
        
//      게시글의 pk 설정
        ArticleVO articleInDBWithPK = articleDao.select(article);
        if (!articleInDBWithPK.isExist()) {
            throw new CustomException("Service - db에 게시글 등록 후 pk 가져오던 중 오류", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
        }
        article.setPk(articleInDBWithPK.getPk());
        

//
//        
        // 파일 등록
        ArticleFileVO articleFileVo = new ArticleFileVO();
        ArticleFileDAO articleFileDao = new ArticleFileDAO();
        for (byte[] file: files) {
            articleFileVo.setExternalArticle(article);
            articleFileVo.setFile(file);
            articleFileDao.insert(articleFileVo);
        }
        
        
        logger.debug("Service - 게시글 등록 완료");
    }
}
