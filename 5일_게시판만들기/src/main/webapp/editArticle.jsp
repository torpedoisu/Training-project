<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시물 수정 페이지</title>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script src="javascript/editArticle.js"></script>
  <script src="javascript/editArticleAC.js"></script>
  <script>
  const loadArticle = {
      url: 'articleDetail.do?pk=<%= request.getParameter("pk") %>',
      method: 'GET',
      data: {},
      callMethod: ['getfileDetailsForEdit', 'loadArticleDetails'] // axios 안에서 불러올 함수
  }
  </script>
</head>
<body>
<h1>게시물 수정 페이지</h1>
    <div id="editArticle">
    <script>processRequest(loadArticle);</script>
        <input type="text" id="editTitle">
        <textarea id="editContent"></textarea>
        <input type="file" id="editFile" multiple onchange="updateFileList(this.files)">
        <ul id="editFileList"></ul>
        <button type="button" id="saveEditBtn" onclick="saveArticleEdit(<%= request.getParameter("pk") %>)">저장</button>
    </div>
</body>
</html>
