package com.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    String charset = null;
    HashMap<String, Controller> list = null;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        charset = sc.getInitParameter("charset");
        list = new HashMap<String, Controller>();
        
        list.put("/userInsert.do", new LoginController());
//TODO:        list.put("/userSearchController", new UserSearchController());
    }

    @Override 
    public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
        req.setCharacterEncoding(charset);

        String url = req.getRequestURI(); // '/5일_게시판만들기/memberInsert.do'
        String contextPath = req.getContextPath(); // '/5일_게시판만들기'
        String path = url.substring(contextPath.length()); // '/memberInsert.do'
        Controller subController = list.get(path);
        
        System.out.println("========== Front Controller path : " + path);
        subController.execute(req, res);
    }

}
