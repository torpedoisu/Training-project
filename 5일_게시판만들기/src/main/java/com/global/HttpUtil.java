package com.global;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class HttpUtil {
    public static Logger logger = LogManager.getLogger(HttpUtil.class);
    
    public static void forward(HttpServletRequest req, HttpServletResponse res, String path) {
        res.setContentType("applicaion/json;charset=UTF-8");
        
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher(path);
            dispatcher.forward(req, res);
        } catch (Exception e) {
            logger.error("forward 발생 중 에러");
            e.printStackTrace();
            
        }
    }
    
    public static void forward(HttpServletRequest req, HttpServletResponse res, PrintWriter out, String path) {
        res.setContentType("applicaion/json;charset=UTF-8");
        
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher(path);
            out.flush();
            dispatcher.forward(req, res);
        } catch (Exception e) {
            logger.error("forward 발생 중 에러");
            e.printStackTrace();
            
        }
    }
}
