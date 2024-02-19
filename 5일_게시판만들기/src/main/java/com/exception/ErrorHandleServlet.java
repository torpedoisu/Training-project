package com.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/errorHandle")
public class ErrorHandleServlet extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();

        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String message = (String) req.getAttribute("javax.servlet.error.message");
        Object type = req.getAttribute("javax.servlet.error.exception_type");
        RuntimeException exception = (RuntimeException) req.getAttribute("javax.servlet.error.exception");
        String uri = (String) req.getAttribute("javax.servlet.error.request_uri");

        
        System.out.println("============== 에러 핸들링 메시지 " + message);
        System.out.println("============== 에러 핸들링 code " + code);
        System.out.println("============== 에러 핸들링 type " + type);
        System.out.println("============== 에러 핸들링 exception " + exception);
        System.out.println("============== 에러 핸들링 uri " + uri);
    }
}