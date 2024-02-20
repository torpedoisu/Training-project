package 기타;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/axios")
public class AxiosServlet extends HttpServlet {
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
        
        PrintWriter out = response.getWriter();
        
// ========================= 테스트 시작===============================
        
        
//         [객체만을 반환] 테스트를 위한 객체 생성
        User user = new User(jsonObject.getString("name"), jsonObject.getInt("age"));
        
//        [객체를 Map에 넣어준 이후 반환] Map을 사용하여 json으로 반환
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("userMap", user);
        
//      [Map을 사용하여 멤버변수만을 반환]
        Map<String, String> userValMap = new HashMap<String, String>();
        userValMap.put(user.getName(), String.valueOf(user.getAge()));
        

        
//        [객체만을 반환] - 실패
        out.print(user);
        
//        [객체를 Map에 넣어준 이후 반환] - 실패
        out.print(userMap);
        
//        [Map을 사용하여 멤버변수만을 반환] - 실패
        out.print(userValMap);
        
        // 응답 헤더 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        out.flush();
        out.close();
    }


	private class User {
		public String name;
		public int age;
		
		public User(String name, int age) {
			this.name = name;
			this.age = age;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getAge() {
			return this.age;
		}
		
		public void setName(String val) {
			this.name = val;
		}
		
		public void setAge(int val) {
			this.age = val;
		}
	}
}