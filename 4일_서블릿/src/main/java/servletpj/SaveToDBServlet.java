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

import db.EmployeeDB;
import response.ResponseData;
import response.Status;

/**
 * Servlet implementation class SaveToDB
 */

public class SaveToDBServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        
        ResponseData responseData = null;
        PrintWriter out = null;
        
        try {
            request.setCharacterEncoding("UTF-8");
            
            
            StringBuilder sb = new StringBuilder();
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            XMLParser parser = new XMLParser(); 
            List<Employee> employees = parser.makeEmployee(sb, "axios"); // 파싱
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            EmployeeDB db = new EmployeeDB();
            responseData = db.syncToEmployeeTable(employees);
            
            
            
            if (responseData.isSuccess()) {
                response.setStatus(HttpServletResponse.SC_OK);    
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            
            out = response.getWriter();
            out.print(responseData.getResponseData());
            
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
