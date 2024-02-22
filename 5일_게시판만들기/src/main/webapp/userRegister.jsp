<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원 가입</title>
  <script src="/javascript/userRegister.js" ></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
<h3>회원 가입</h3>

${error}

<form action="userInsert.do" method="post">
    ID: <input type="text" name="id" required> <br>
    Password: <input type="password" name="pwd" required><br>
    <button type="submit">가입</button>
</form>
</body>
</html>