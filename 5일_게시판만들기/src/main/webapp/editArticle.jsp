<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시물 수정 페이지</title>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script src="javascript/editArticle.js"></script>
  <script>
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        const articlePk = urlParams.get('pk');
        getArticleForEdit(articlePk);
        // 작성자 확인 해야함
    };s
  </script>
</head>
<body>
<h1>게시물 수정 페이지</h1>
    <div id="editArticle">
        <input type="text" id="editTitle">
        <textarea id="editContent"></textarea>
        <input type="file" id="editFile" multiple onchange="updateFileList(this.files)">
        <ul id="editFileList"></ul>
        <button type="button" id="saveEditBtn" onclick="saveArticleEdit()">저장</button>
    </div>
</body>
</html>
