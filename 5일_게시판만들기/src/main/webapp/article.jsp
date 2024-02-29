<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시물 상세 페이지</title>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script src="javascript/article.js"></script>
  <script src="javascript/articleAC.js"></script>
  
  <script>
    const getArticleDetails = {
        url: 'articleDetail.do?pk=<%= request.getParameter("pk") %>',
        method: 'GET',
        callMethod: ['displayArticleDetails', 'getfileDetails', 'checkIfUserIsIdentical'] // axios 안에서 불러올 함수
    };
  </script>
</head>
<body>
<h1>게시물 상세 페이지</h1>

    <div id="articleDetail">
    <script>processRequest(getArticleDetails);</script>
        <h2 id="articleTitle"></h2>
        <p id="articleUser"></p>
        <p id="articleContent"></p>
    </div>
    <button type="button" onclick="redirectToEditArticle('<%= request.getParameter("pk") %>')" id="updateBtn" style="display: none">수정</button>
    <button type="button" onclick="deleteArticle('<%= request.getParameter("pk") %>')" id="deleteBtn" style="display: none">삭제</button>
    
    <br><br>
    
    <h3>파일</h3>
    <ul id="articleFileLink"></ul>
</body>