package com.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.service.ArticleService;

public class ArticleDeleteController implements Controller{

    public static Logger logger = LogManager.getLogger(ArticleDeleteController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("ArticleDeleteController 진입");
        
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        
        JSONObject jsonRequest = new JSONObject(sb.toString());
        String articlePk = jsonRequest.getString("pk");
        
        ArticleService articleService = ArticleService.getInstance();
        
        articleService.delete(articlePk);
        
        req.setAttribute("path", "index.jsp");
        
    }

}
