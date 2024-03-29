package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.exception.CustomException;
import com.service.ArticleService;
import com.vo.ArticleFileVO;
import com.vo.ArticleVO;

public class ArticleDetailController implements Controller {

    public static Logger logger = LogManager.getLogger(ArticleDetailController.class);
    
    /**
     * 게시글의 상세 내용을 반환하는 메서드
     * 
     * 예외 처리 - 파일이 첨부되지 않은 경우 확인
     */
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("ArticleDetailController 진입");
        
        // 게시물 ID 파라미터 가져오기
        String articlePk = req.getParameter("pk");

        ArticleService articleService = ArticleService.getInstance();
        ArticleVO articleVo = articleService.getArticleAndFiles(articlePk);

        if (articleVo == null) {
            throw new CustomException("삭제된 게시글입니다", HttpServletResponse.SC_BAD_REQUEST, "index.jsp");
        }

        JSONObject responseObject = new JSONObject();
        responseObject.put("pk", articlePk);
        responseObject.put("title", articleVo.getTitle());
        responseObject.put("content", articleVo.getContent());
        responseObject.put("user", articleVo.getExternalUser().getId());

        
        JSONObject fileObject = new JSONObject();
        List<ArticleFileVO> articleFiles = articleVo.getExternalFiles();
        
        // 파일(바이트 배열)을 Base64로 인코딩하여 JSON에 추가
        if ((articleFiles != null) && (articleFiles.size() != 0)) {
            for (ArticleFileVO articleFile : articleFiles) {
                String encodedFile = Base64.getEncoder().encodeToString(articleFile.getFile());

                fileObject.put(articleFile.getTitle(), encodedFile);
            }
        }
        responseObject.put("files", fileObject);

        PrintWriter out = res.getWriter();
        out.write(responseObject.toString());

        req.setAttribute("path", "article.jsp");
        req.setAttribute("PrintWriter", out);
        
        logger.debug("ArticleDetailController 완료");
    }


}
