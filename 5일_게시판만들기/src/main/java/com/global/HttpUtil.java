package com.global;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level; // ¿ä³ð Ãß°¡
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator; // ¿ä³ð Ãß°¡


public class HttpUtil {
    public static Logger logger = LogManager.getLogger("HttpUtil.class");
    
    public static void forward(HttpServletRequest req, HttpServletResponse res, String path) {
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher(path);
            dispatcher.forward(req, res);
        } catch (Exception e) {
            logger.debug("[Error] forward ¿À·ù");
        }
    }
}
