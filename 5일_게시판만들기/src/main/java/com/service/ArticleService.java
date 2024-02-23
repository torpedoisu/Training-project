package com.service;

import java.sql.Blob;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.controller.ArticleRegisterController;
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
    
    public void registerArticle(UserVO user, String title, String content, List<Blob> files) {
        
        logger.debug("게시글 등록 Service 시작");
        
        boolean userIsValid = UserService.getInstance().checkUserIsValidInSession(user.getPk(), user.getId(), user.getPwd());
        
        if (!userIsValid) {
            throw new CustomException("세션이 잘못되었습니다 다시 로그인해주세요.", HttpServletResponse.SC_FORBIDDEN, "login.jsp");
        }

        content = (content == null) ? "" : content;
        title = (title == null) ? "" : content;
        
        ArticleVO articleVo = new ArticleVO();
        articleVo.setExternalUser(user);
        articleVo.setTitle(title);
        articleVo.setContent(content);

        ArticleDAO articleDao = new ArticleDAO();
        ArticleVO  insertedArticle = articleDao.insert(articleVo);
        
//        
//        String articlePk = articleDao.select(articleVo); //TODO
//        articleVo.setPk(articlePk);
//
//        
//        // 파일 등록
//        ArticleFileVO articleFileVo = new ArticleFileVO();
//        ArticleFileDAO articleFileDao = new ArticleFileDAO();
//        for (Blob file: files) {
//            articleFileVo.setExternalArticle(articleVo);
//            articleFileVo.setFile(file);
//            articleFileDao.insert(articleFileVo);
//        }
//        
        
        
    }
}
