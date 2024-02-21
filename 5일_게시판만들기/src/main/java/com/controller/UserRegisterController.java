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

public class UserRegisterController implements Controller{
    
    public static Logger logger = LogManager.getLogger(UserRegisterController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        logger.debug("userRegisterController 진입");
        
        String id = req.getParameter("id").trim();
        String pwd = req.getParameter("pwd").trim();
        
        if (id.isEmpty() || pwd.isEmpty()) {
            req.setAttribute("error", "모든 항목을 입력해주세요");
            HttpUtil.forward(req, res, "/userInsert.jsp");
        }
        
        UserVO user = new UserVO();
        user.setId(id);
        user.setPwd(pwd);
        
        // 유저 db에 등록
        UserService service = UserService.getInstance();
        UserVO userWithPk = service.userInsert(user);
        
        // 유저 정보 세션으로 전송
        HttpSession session = req.getSession();
        session.setAttribute("user", userWithPk);

        PrintWriter out = res.getWriter();
        req.setAttribute("id", id);
        HttpUtil.forward(req, res, "index.jsp");
    }

}
