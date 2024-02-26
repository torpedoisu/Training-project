package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exception.CustomException;
import com.global.HttpUtil;
import com.vo.UserVO;

public class UserAuthController implements Controller{

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
       
        if (user == null) {
            throw new CustomException("다시 로그인 해주세요", HttpServletResponse.SC_BAD_REQUEST, "login.jsp");
        }
        
        HttpUtil.forward(req, res, "index.jsp");
        
    }

}
