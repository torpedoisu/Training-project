package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.vo.UserVO;

public class UserAuthInIndexController implements Controller{

    public static Logger logger = LogManager.getLogger(UserAuthInIndexController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("UserAuthInIndexController 진입");
        
        JSONObject jsonObject = new JSONObject();
        PrintWriter out = res.getWriter();
        
        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        if (user == null) {
            jsonObject.put("isExist", false);
            out.print(jsonObject);
            logger.debug("유저 세션이 브라우저에 존재하지 않음");
            return;
        }
        
        jsonObject.put("isExist", true);
        out.print(jsonObject);
        logger.debug("유저 세션이 브라우저에 존재함");
        
        logger.debug("UserAuthInIndexController 완료");
    }

}
