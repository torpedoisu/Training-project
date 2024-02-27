<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="ajaxTest.js"></script>
<meta charset="UTF-8">
<title>ajax 라이브러리 테스트</title>
</head>
<body>

<button type ="submit" onclick="jQueryAjax()">jQuery의 ajax</button>
<button type = "submit" onclick="axiosJson()">axios의 json 자동 변환</button>
<button type = "submit" onclick="vanillaAjax()">바닐라 js로 ajax 구현</button>

</body>
</html>