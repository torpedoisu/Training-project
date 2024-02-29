<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <script src="javascript/login.js" ></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<title>login</title>
</head>
<body>
    <form method="post">
      ID: <input type="text" id="id" required> <br>
      Password: <input type="password" id="pwd" required><br>
      <button type="button" onclick="login()">로그인</button>
    </form>
</body>
</html>