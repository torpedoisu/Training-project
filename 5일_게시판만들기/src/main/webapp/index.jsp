<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시판</title>
  
  <script src="javascript/indexAC.js"></script>
  <script src="javascript/index.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <link rel="stylesheet" type="text/css" href="css/index.css">
  <script>
  
  const loadArticles = {
      url: 'articlesLoad.do',
      method: 'GET',
      data: {},
      callMethod: 'displayArticles' // axios 안에서 불러올 함수
  }
  
  const checkUserInSession = {
    url: 'userAuthInIndex.do',
    method: 'GET',
    data: {},
    callMethod: 'loadIndexBtn' // axios 안에서 불러올 함수
  }
  </script>
  
</head>
<body>
    <h1>게시판</h1>
    
    <script>processRequest(loadArticles);</script>
    <script>processRequest(checkUserInSession);</script>
    
    <table id="articleTable">
    
    <button type="button" onclick="logout()" id="logoutButton" style="display: none">로그아웃</button>
    <button type="button" onclick="redirectToLogin()" id="loginButton" style="display: none">로그인</button>
    <button type="button" onclick="redirectToRegister()" id="registerButton" style="display: none">회원가입</button>
    
        <thead>
            <tr>
                <th>작성자</th>
                <th>제목</th>
                <th>본문</th>
                <th>바로가기</th>
            </tr>
        </thead>
        <tbody id="articleTableBody"></tbody>
        
        <button type="button" onclick="redirectToPost()" id="postButton" style="display: none">게시글 작성</button>
    </table>
</body>
</html>