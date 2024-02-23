package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class ArticleRegisterController implements Controller {
    
    public static Logger logger = LogManager.getLogger(ArticleRegisterController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        

            String contentType = req.getContentType();
            if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
                Collection<Part> parts = req.getParts();
                
                for (Part part : parts) {
                   if (part.getHeader("Content-Disposition").contains("filename=")) {
                       Part file = req.getPart(part.getName());
                       String fileInString = extractFormData(file);
                       logger.debug(part.getName() + " " + fileInString);

                    } else {
                      String formValue = req.getParameter(part.getName());  
                      logger.debug(part.getName() + " " + formValue);

                    }
                   
                }
            }
        
        
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
