package servletpj;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import response.ResponseData;
import response.Status;

/**
 * Servlet implementation class AddressBook
 */

@MultipartConfig
public class EmployeeBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        RequestDispatcher dispatcher = null;
        
        String fileName = filePart.getSubmittedFileName().trim();
        // 파일이 업로드되지 않은 채로 채줄 된 경우
        if (fileName.isEmpty()) {
            ResponseData responseData = new ResponseData(Status.FAIL, "파일이 업로드되지 않았습니다. 파일을 업로드 후 제출해주세요.");
            request.setAttribute("responseData", responseData);
            dispatcher = getServletContext().getRequestDispatcher("/");
            dispatcher.forward(request, response);
            return;
        }
        
        InputStream fileContent = filePart.getInputStream();
        
        XMLParser parser = new XMLParser();
        List<EmployeeVO> employees;
        employees = parser.parseXML(fileContent); // 스트림을 XMLParser에 전달
        
        
        // 형식에 맞지 않는 XML 파일인 경우
        if (employees == null) { 
            ResponseData responseData = new ResponseData(Status.FAIL, "형식에 맞지 않는 XML 파일입니다. 파일 확인 후 업로드해주세요.");
            request.setAttribute("responseData", responseData);
            dispatcher = getServletContext().getRequestDispatcher("/");
        } else {
            request.setAttribute("employees", employees);
            dispatcher = getServletContext().getRequestDispatcher("/employeeTable.jsp");
        }

        dispatcher.forward(request, response);
    }

}
