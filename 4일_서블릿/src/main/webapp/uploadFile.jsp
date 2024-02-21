<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="response.ResponseData" %>    

<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
    <script src="javascript/uploadFile.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<meta charset="UTF-8">
</head>
<body>


  <form id="uploadForm" action="employeebook" method="post" enctype="multipart/form-data">
    <input type="file" name="file" id="fileUploader" />
    <button type="submit" onclick="submitFile(this)">파일 제출</button>
  </form>

<%
    // 서블릿에서 반환하는 값이 없다면 에러
    ResponseData responseData = (ResponseData) request.getAttribute("responseData");
    if (responseData != null && !responseData.isSuccess()) {
%>
 <script language="JavaScript">
        var errorData = <%= responseData.getResponseData() %>;
        alert(errorData.statusDescription);
    </script>
<%
    }
%>

</body>
</html>