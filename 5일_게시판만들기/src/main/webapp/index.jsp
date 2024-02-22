<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.vo.ArticleVO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    
    <script src="javascript/board.js"></script>
    <!-- JavaScript로 서버에서 게시글 목록을 비동기적으로 가져오는 함수 호출 -->
    <script>
        getPostList();
    </script>
    
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
                List<ArticleVO> articles = (List<ArticleVO>) request.getAttribute("articles");
                if (articles != null) {
                    for (ArticleVO article : articles) {
            %>
            <tr>
                <td><%= article.getExternalUser().getId() %></td>
                <td><%= article.getTitle() %></td>
                <td><%= article.getContent() %></td>
            </tr>
            <%-- 게시글을 테이블에 출력하는 부분 끝 --%>
            <% 
                    }
                }
            %>
        </tbody>

</body>
</html>