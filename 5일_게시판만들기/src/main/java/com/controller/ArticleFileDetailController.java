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

import com.service.ArticleFileService;
import com.vo.ArticleFileVO;

public class ArticleFileDetailController implements Controller{

    public static Logger logger = LogManager.getLogger(ArticleFileDetailController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("ArticleFileDetailController 진입");
        
        String articlePk = req.getParameter("pk");
        
        ArticleFileService articleFileService = ArticleFileService.getInstance();
        List<ArticleFileVO> articleFilesVo = articleFileService.getFiles(articlePk);
        
        
        JSONObject responseObject = new JSONObject();
        JSONObject fileObject = new JSONObject();
    
        if (articleFilesVo == null) {
            res.setStatus(HttpServletResponse.SC_CONTINUE);
            logger.debug("게시글 pk: " + articlePk + " 에 대한 파일 존재하지 않음");
            return;
        }
        int index = 0;
        for (ArticleFileVO articleFile : articleFilesVo) {
            String encodedFile = Base64.getEncoder().encodeToString(articleFile.getFile());
            fileObject.put(articleFile.getTitle(), encodedFile);
        }
        
        responseObject.put("files", fileObject);
        
        PrintWriter out = res.getWriter();
        out.print(responseObject.toString());
        
        logger.debug("ArticleFileDetailController 완료");
    }

}
