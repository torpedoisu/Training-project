package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.exception.CustomException;
import com.vo.UserVO;

public class UserAuthController implements Controller{

    /**
     * 유저의 세션이 존재하는지 확인하는 메서드
     * 
     * 예외 처리
     * - 유저 세션이 존재하는지 확인
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
       
        if (user == null) {
            throw new CustomException("다시 로그인 해주세요", HttpServletResponse.SC_BAD_REQUEST, "login.jsp");
        }
        
        PrintWriter out = res.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("isExist", true);
        
        out.print(jsonObject);
        req.setAttribute("path", "index.jsp");
        
    }

}
