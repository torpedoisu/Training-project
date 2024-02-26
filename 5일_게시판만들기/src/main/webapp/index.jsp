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
  <link rel="stylesheet" type="text/css" href="css/index.css">
  <script>
    window.onload = function() {
          loadIndexButtons();
          loadArticles();
      };
  </script>
  
</head>
<body>
    <h1>게시판</h1>
    <table id="articleTable">

    <button type="button" onclick="logout()" id="logoutButton" style="display: none">로그아웃</button>
    <button type="button" onclick="redirectToLogin()" id="loginButton" style="display: none">로그인</button>
    <button type="button" onclick="redirectToRegister()" id="registerButton" style="display: none">회원가입</button>
    
        <thead>
            <tr>
                <th>작성자</th>
                <th>제목</th>
                <th>본문</th>
            </tr>
        </thead>
        <tbody id="articleTableBody"></tbody>
        <button type="button" onclick="redirectToPost()" id="postButton" style="display: none">게시글 작성</button>
        

</body>
</html>