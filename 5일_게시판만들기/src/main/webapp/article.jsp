<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시물 상세 페이지</title>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script src="javascript/article.js"></script>
  <script>
        window.onload = function() {
            const urlParams = new URLSearchParams(window.location.search);
            const articlePk = urlParams.get('pk');
            getArticleDetails(articlePk);
        };
    </script>
</head>
<body>
<h1>게시물 상세 페이지</h1>
    <div id="articleDetail">
        <h2 id="articleTitle"></h2>
        <p id="articleUser"></p>
        <p id="articleContent"></p>
    </div>
    <button type="button" id="updateBtn" style="display: none">수정</div>
    <button type="button" id="deleteBtn" style="display: none">삭제</div>
    <div type="button" id="articleFileLink"></div>
</body>