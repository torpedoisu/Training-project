<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시글 작성</title>
  <script src="javascript/post.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script>
    const checkUserInPost = {
        url: 'userAuthInIndex.do',
        method: 'GET',
        data: {},
        callMethod:  // axios 안에서 불러올 함수
    }
  </script>
  
  <style>
        .editor {
            width: 100%;
            height: 300px;
            border: 1px solid #ccc;
            padding: 5px;
            overflow-y: auto;
        }
    </style>
</head>
<body>
  <h1>게시글 작성</h1>
  <script>processRequest(checkUserInPost)</script>
  <form id="articleForm">
    <label for="title">제목:</label><br>
    <input type="text" id="title" name="title" required><br><br>
    
    <input type="textarea" id="content" name="content"  class="editor" required></input>

    <label for="file">파일 선택:</label>
    <input type="file" id="file" name="file" multiple onchange="updateFileList(this.files)"><br>
    <ul id="fileList"></ul>
    
    <button type="button" onclick="registerPost()">작성 완료</button>
  </form>
</body>
</html>
