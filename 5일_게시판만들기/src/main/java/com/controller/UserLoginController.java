package com.controller;

import java.io.BufferedReader;
import java.io.IOException;

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

public class UserLoginController implements Controller {

    public static Logger logger = LogManager.getLogger(UserLoginController.class);

    /*
     * user 객체를 세션에 등록해줌으로써 로그인을 구현한 로그인 컨트롤러
     * 
     * 예외 처리
     * - id와 password가 존재하는지 검증 (없다면 다시 입력하도록 라우팅)
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("LoginController 진입");

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
        
        // ID 또는 비밀번호가 입력되지 않은 경우
        if (userId == null || userPwd == null || userId.trim().isEmpty() || userPwd.trim().isEmpty()) {
            throw new CustomException("ID 또는 비밀번호를 입력해주세요", HttpServletResponse.SC_BAD_REQUEST, "login.jsp");
        }

        // UserService를 사용하여 사용자 정보 가져오기 (service에서 검증)
        UserService userService = UserService.getInstance();
        UserVO user = userService.login(userId, userPwd); 
        
        // 세션에 사용자 정보 저장
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(60 * 60 * 10); // 10시간
        session.setAttribute("user", user);

        // 로그인 성공 메시지 출력
        res.setStatus(HttpServletResponse.SC_OK);
        
        logger.debug("로그인 완료");
        HttpUtil.forward(req, res, "index.jsp");
    }
}
