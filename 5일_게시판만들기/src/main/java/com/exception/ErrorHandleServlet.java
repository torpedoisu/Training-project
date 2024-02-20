package com.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.HttpUtil;
import com.global.ResponseData;
import com.global.Status;

@WebServlet("/errorHandle")
public class ErrorHandleServlet extends HttpServlet{
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        PrintWriter out = res.getWriter();
        
        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String message = (String) req.getAttribute("javax.servlet.error.message");
        Object type = req.getAttribute("javax.servlet.error.exception_type");
//        Exception exception = (Exception) req.getAttribute("javax.servlet.error.exception");

        // 보낼 헤더 설정
        res.setContentType("applicaion/json;charset=UTF-8");
        res.setStatus(code);
        
        // 보낼 바디 설정
        ResponseData responseData = new ResponseData(Status.FAIL, message, type);
        out.print(responseData.getJsonResponseData());
        out.flush();
        out.close();
        
        System.out.println(responseData.getJsonResponseData());
        HttpUtil.forward(req, res, "/result/memberInsertOutput.jsp");

    }
}