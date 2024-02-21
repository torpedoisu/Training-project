package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.global.HttpUtil;
import com.service.UserService;
import com.vo.UserVO;

public class LogoutController implements Controller{

    public static Logger logger = LogManager.getLogger(LogoutController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        PrintWriter out = res.getWriter();
        
        // 유저 정보 받아오기
        HttpSession session = req.getSession();
        
        if (session != null && session.getAttribute("user") != null) {
            session.invalidate();
            out.print("로그아웃 작업 완료하였습니다");
        } else {
            out.print("현재 로그인 상태가 아닙니다");
        }
        HttpUtil.forward(req, res, "index.jsp");
    }

}
