package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.CustomException;
import com.global.HttpUtil;
import com.global.ResponseData;
import com.global.Status;

public class UserLogoutController implements Controller{

    public static Logger logger = LogManager.getLogger(UserLogoutController.class);
    
    /*
     * 세션을 유효하지 않게 만듬으로써 로그아웃 기능을 구현한 로그아웃 컨트롤러
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        logger.debug("LogOutController 진입");
        
        // 유저 정보 받아오기
        HttpSession session = req.getSession();
        
        // 유저가 정상적으로 로그인 되어 있는 경우
        if (session != null && session.getAttribute("user") != null) {
            session.invalidate();
            res.setStatus(HttpServletResponse.SC_OK);
        } 
        
        HttpUtil.forward(req, res, "index.jsp");
    }

}
