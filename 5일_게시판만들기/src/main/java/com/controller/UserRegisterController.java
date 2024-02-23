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

import com.exception.CustomException;
import com.global.HttpUtil;
import com.service.UserService;
import com.vo.UserVO;

public class UserRegisterController implements Controller{
    
    public static Logger logger = LogManager.getLogger(UserRegisterController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        logger.debug("userRegisterController 진입");
        
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        
        // JSON 데이터를 JSONObject로 파싱
        JSONObject jsonRequest = new JSONObject(sb.toString());
        String userId = jsonRequest.getString("id");
        String userPwd = jsonRequest.getString("pwd");
        
        if (userId.trim().isEmpty() || userId.trim().isEmpty()) {
            throw new CustomException("비밀번호 혹은 아이디 채워지지 않음", HttpServletResponse.SC_BAD_REQUEST, "userRegister.jsp");
        }
        
        // 유저 db에 등록
        UserService userService = UserService.getInstance();
        UserVO userInDB = userService.register(userId, userPwd);
        
        // 유저 정보 세션으로 전송
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(60 * 60 * 10); 
        session.setAttribute("user", userInDB);
        
        res.setStatus(HttpServletResponse.SC_OK);
        logger.debug("회원 가입 완료");
        HttpUtil.forward(req, res, "index.jsp");
    }

}
