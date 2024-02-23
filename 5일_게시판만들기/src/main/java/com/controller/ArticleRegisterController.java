package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ArticleRegisterController implements Controller {
    
    public static Logger logger = LogManager.getLogger(UserLogoutController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String form = req.getParameter("formData");
        logger.debug("form - " + form);
        
        Part formPart = req.getPart("formData");
        logger.debug("formPart" + formPart);
        
        String title = req.getParameter("title");
        logger.debug("title - " + title);
        
//        Part titlePart = req.getPart("title");
//        String title = extractFormData(titlePart);
//        
        Part contentPart = req.getPart("content");
        logger.debug("content - " + contentPart);  
//      
//        String content = extractFormData(contentPart);
//        List<Part> fileParts = new ArrayList<>();
//        Collection<Part> parts = req.getParts();
//        for (Part part: parts) {
//            if (part.getName().equals("file")) {
//                fileParts.add(part);
//            }
//        }
//        
//        
    }
    
    private String extractFormData(Part part) throws IOException {
        if (part != null) {
            InputStream inputStream = part.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            return sb.toString();
        }
        return null;
    }

}
