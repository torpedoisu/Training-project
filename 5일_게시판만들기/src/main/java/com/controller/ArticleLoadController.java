package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exception.CustomException;
import com.global.HttpUtil;
import com.service.ArticleService;
import com.vo.ArticleVO;

public class ArticleLoadController implements Controller{

    public static Logger logger = LogManager.getLogger(ArticleLoadController.class);
    
    /**
     * 작성자, 제목, 본문 정보를 담은 article 객체 반환하는 메서드
     * 

     * 
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("전체 게시글 조회 시작");
        
        PrintWriter out = res.getWriter();
        
        ArticleService articleService = ArticleService.getInstance();
        List<ArticleVO> articles = articleService.getArticles();
        
        
        JSONArray jsonArray = new JSONArray();
        for (ArticleVO article : articles) {
            JSONObject jsonArticle = new JSONObject();
            jsonArticle.put("user", article.getExternalUser().getId());
            jsonArticle.put("title", article.getTitle());
            jsonArticle.put("content", article.getContent());
            
            jsonArray.put(jsonArticle);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("articles", jsonArray);
        
        out.print(jsonResponse);
        HttpUtil.forward(req, res, out, "index.jsp");
    }

}