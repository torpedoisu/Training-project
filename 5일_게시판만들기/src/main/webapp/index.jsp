<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java." %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    <script src="javascript/board.js"></script>
</head>
<body>
    <h1>게시판</h1>
    <table id="articleTable">
        <thead>
            <tr>
                <th>글 번호</th>
                <th>제목</th>
                <th>작성자</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Post> postList = (List<Post>) request.getAttribute("postList");
                if (postList != null) {
                    for (Post post : postList) {
            %>
            <tr>
                <td><%= post.getId() %></td>
                <td><%= post.getTitle() %></td>
                <td><%= post.getWriter() %></td>
                <td><%= post.getCreatedAt() %></td>
            </tr>
            <%-- 게시글을 테이블에 출력하는 부분 끝 --%>
            <% 
                    }
                }
            %>
        </tbody>
    </table>
    <!-- JavaScript로 서버에서 게시글 목록을 비동기적으로 가져오는 함수 호출 -->
    <script>
        getPostList();
    </script>
</body>
</html>