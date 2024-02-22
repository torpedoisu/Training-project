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
import com.service.UserService;
import com.vo.UserVO;

public class UserLoginController implements Controller {

    public static Logger logger = LogManager.getLogger(UserLoginController.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("LoginController 진입");

        // 세션 가져오기
        HttpSession session = req.getSession();
        Object sessionUser = session.getAttribute("user");
        
        String userId = req.getParameter("id").trim();
        String userPwd = req.getParameter("pwd").trim();
        
        // ID 또는 비밀번호가 입력되지 않은 경우
        if (userId == null || userPwd == null || userId.isEmpty() || userPwd.isEmpty()) {
            throw new CustomException("ID 또는 비밀번호를 입력해주세요", HttpServletResponse.SC_BAD_REQUEST, "/login.jsp");
        }

        // UserService를 사용하여 사용자 정보 가져오기
        UserService userService = UserService.getInstance();
        UserVO user = userService.getUserInDB(userId, userPwd);

        if (!user.isExist()) {
            throw new CustomException("잘못된 사용자입니다", HttpServletResponse.SC_BAD_REQUEST, "/login.jsp");
        }
        // 세션에 사용자 정보 저장
        session.setAttribute("user", user);

        // 로그인 성공 메시지 출력
        res.setStatus(HttpServletResponse.SC_OK);
        
        HttpUtil.forward(req, res, "/index.jsp");
    }
}
