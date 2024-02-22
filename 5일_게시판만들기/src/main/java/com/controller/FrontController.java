package com.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FrontController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    String charset = null;
    HashMap<String, Controller> list = null;
    
    public static Logger logger = LogManager.getLogger(FrontController.class);
    

    @Override
    public void init(ServletConfig sc) throws ServletException {
        charset = sc.getInitParameter("charset");
        list = new HashMap<String, Controller>();
        
        // 회원 정보 관리 컨트롤러 
        list.put("/userRegister.do", new UserRegisterController());
        list.put("/userLogin.do", new UserLoginController());
        list.put("/userLogout.do", new UserLogoutController());
        list.put("/userAuth.do", new UserAuthController());
        
        // 게시글 관련 컨트롤러
        list.put("/articleRegister.do", new ArticleRegisterController());
    }

    @Override 
    public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
        req.setCharacterEncoding(charset);
        res.setCharacterEncoding(charset);
        
        String url = req.getRequestURI(); // '/5일_게시판만들기/userInsert.do'
        String contextPath = req.getContextPath(); // '/5일_게시판만들기'
        String path = url.substring(contextPath.length()); // '/userInsert.do'
        Controller subController = list.get(path);
        
        logger.debug("FrontController - " + path + "로 라우팅 시작");
        
        subController.execute(req, res);
        return;
    }

}
