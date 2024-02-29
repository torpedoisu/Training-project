<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원 가입</title>
  <script src="javascript/userRegister.js" ></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
<h3>회원 가입</h3>
    <form method="post">
      ID: <input type="text" id="id" required> <br>
      Password: <input type="password" id="pwd" required><br>
      <button type="button" onclick="register()">가입</button>
    </form>
    
</body>
</html>