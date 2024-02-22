<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <script src="/javascript/login.js" ></script>
<title>login</title>
</head>
<body>
    <form action="userLogin.do" method="post">
    ID: <input type="text" name="id" required> <br>
    Password: <input type="password" name="pwd" required><br>
    <button type="submit">로그인</button>
</form>
</body>
</html>