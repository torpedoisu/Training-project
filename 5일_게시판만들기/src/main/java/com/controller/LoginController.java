package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.HttpUtil;
import com.vo.UserVO;

public class LoginController implements Controller{

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession();
        
        if(session == null) {
            out.print("잘못된 회원정보입니다");
            out.flush();
            out.close();
            
            //TODO: 어디로보낼지 생각
            HttpUtil.forward(req, res, "/index.jsp");
        }
        
        
        Object obj = session.getAttribute("user");
        if (obj instanceof UserVO) {
            UserVO user = (UserVO) obj;
        } else {
            
        }
        
        
        
    }

}
