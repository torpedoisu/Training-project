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

/**
 * Servlet implementation class AddressBook
 */

@WebServlet("/AddressBook")
@MultipartConfig
public class AddressBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Part filePart = request.getPart("file");
	    InputStream fileContent = filePart.getInputStream();

	    XMLParser parser = new XMLParser();
	    List<Employee> employees;
	    
	    try {
	        employees = parser.parseXML(fileContent); // 스트림을 XMLParser에 전달
	        request.setAttribute("employees", employees);
	    } catch (Exception e){
	        e.printStackTrace(); // TODO: 형식 지키지 않은 xml의 경우 예외 처리 
	    }
	    
	    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/employeeTable.jsp");
	    dispatcher.forward(request, response);
	}

}
