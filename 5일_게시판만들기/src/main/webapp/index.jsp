<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.vo.ArticleVO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시판</title>
  
  <script src="javascript/article.js"></script>
  <script src="javascript/user.js" ></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script>
      getPostList();
  </script>
  
</head>
<body>
    <h1>게시판</h1>
    <table id="articleTable">
    <button type="button" id="logout">로그아웃</button>
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
        <br>
        <button type="button" id="registerArticle">게시글 작성</button>
        

</body>
</html>