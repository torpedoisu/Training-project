package 기타;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/common")
public class CommonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 본문 읽기
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) { 
        	e.printStackTrace();
        }

        // JSON 파싱
        String jsonString = sb.toString();
        JSONObject jsonObject = new JSONObject(jsonString);

        // 응답 헤더 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 응답 본문 작성
        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString()); // 요청 본문을 그대로 응답 본문으로 전달
    }
}
