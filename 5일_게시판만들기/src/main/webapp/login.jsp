<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
</head>
<body>
<h3>회원 가입</h3>

${error}

<form action="userInsert.do" method="post">
    ID: <input type="text" name="id"> <br>
    Password: <input type="text" name="pwd"><br>
    <input type="submit" value="가입">
</form>
</body>
</html>