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

/**
 * Servlet implementation class SaveToXMLServlet
 */
@WebServlet("/SaveToXML")
public class SaveToXMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        System.out.println("Reading JSON...");
        
        // JSON 읽어오기
        StringBuilder sb = new StringBuilder();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        XMLParser parser = new XMLParser();
        StringBuilder xml = parser.makeXML(sb);
        
        response.setContentType("application/xml; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xml");
        PrintWriter out = response.getWriter();
        out.print(xml.toString());
        out.close();
    }
}
