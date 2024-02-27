package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exception.CustomException;
import com.global.HttpUtil;
import com.vo.UserVO;

public class UserCheckSameController implements Controller{

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        // article 정보 받아와서 db에 있는 거 꺼내온 다음 세션에 있는 유저 정보랑 같은지 확인
        
        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        
        req.setAttribute("path", "index.jsp");
    }

}
