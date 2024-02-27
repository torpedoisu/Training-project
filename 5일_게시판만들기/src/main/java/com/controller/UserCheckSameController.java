package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.service.ArticleService;
import com.vo.ArticleVO;
import com.vo.UserVO;

public class UserCheckSameController implements Controller{

    public static Logger logger = LogManager.getLogger(UserCheckSameController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("UserCheckSameController 진입");
        
        JSONObject jsonObject = new JSONObject();
        PrintWriter out = res.getWriter();
        
        // article 정보 받아와서 db에 있는 거 꺼내온 다음 세션에 있는 유저 정보랑 같은지 확인
        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        if (user == null) {
            jsonObject.put("isSameUser", false);
            out.print(jsonObject);
            logger.debug("userID가 브라우저에 존재하지 않음");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        
        // JSON 데이터를 JSONObject로 파싱
        JSONObject jsonRequest = new JSONObject(sb.toString());
        String articlePk = jsonRequest.getString("pk");
        
        ArticleService articleService = ArticleService.getInstance();
        ArticleVO articleVo = articleService.getArticle(articlePk);
        
        if (articleVo == null) {
            jsonObject.put("isSameUser", false);
            out.print(jsonObject);
            logger.debug("userID: " + user.getId() +"와 조회 Id: " + articleVo.getExternalUser().getId() + " 불일치");
            return;
        }
        
        if (user.getId().equals(articleVo.getExternalUser().getId())) {
            jsonObject.put("isSameUser", true);
            logger.debug("userID: " + user.getId() +"와 조회 Id 일치");
        }
        
        out.print(jsonObject);
        
        logger.debug("UserCheckSameController 완료");
    }

}
