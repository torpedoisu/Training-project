package servletpj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import response.ResponseData;
import response.Status;

/**
 * Servlet implementation class SaveToXMLServlet
 */

public class SaveToXMLServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        ResponseData responseData = null;
        PrintWriter out = null;
        
        try {
            
            System.out.println("Reading JSON...");
            
            // JSON 읽어오기
            StringBuilder sb = new StringBuilder();
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            XMLParser parser = new XMLParser();
            String xml = parser.makeXML(sb);
            
            response.setContentType("application/xml;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=employees.xml");
            out = response.getWriter();
            out.print(xml);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseData = new ResponseData(Status.FAIL, "서블릿 처리 도중 예외 발생");
        
            try {
                out = response.getWriter();
                out.print(responseData.getResponseData());
            } catch (IOException ex) {
                ex.printStackTrace();
            } 
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        
        }
        
    }
}
