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
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        logger.debug("LogOutController 진입");
        
        // 유저 정보 받아오기
        HttpSession session = req.getSession();
        
        // 유저가 정상적으로 로그인 되어 있는 경우
        if (session != null && session.getAttribute("user") != null) {
            session.invalidate();
            res.setStatus(HttpServletResponse.SC_OK);
            
        // 유저가 로그인 되어 있지 않은 경우
        } else if (session != null){
            throw new CustomException("현재 로그인 상태가 아닙니다", HttpServletResponse.SC_BAD_REQUEST, "/index.jsp");
            
        // 유저의 세션 정보가 손상된 경우
        } else if (session.getAttribute("user") != null) {
            throw new CustomException("유저 정보에 문제가 있습니다", HttpServletResponse.SC_BAD_REQUEST, "/index.jsp");
        }
        
        
        HttpUtil.forward(req, res, "index.jsp");
    }

}
