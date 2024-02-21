<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="servletpj.EmployeeVO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>

<title>Employee Book</title>

<script src="javascript/employeeTable.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>


<meta charset="UTF-8">
</head>
<body>
  <table id="employeeBook">
    <thead>
      <tr>
        <th>부서</th>
        <th>이름</th>
        <th>직책</th>
        <th>영문이름</th>
        <th>휴대폰</th>
        <th>메일주소</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <% List<EmployeeVO> employees = (ArrayList<EmployeeVO>) request.getAttribute("employees");
      for(EmployeeVO employee : employees) { %>
      <tr>
        <td><%= employee.getDepartment()%></td>
        <td><%= employee.getName()%></td>
        <td><%= employee.getPosition()%></td>
        <td><%= employee.getEnglishName()%></td>
        <td><%= employee.getPhoneNumber()%></td>
        <td><%= employee.getEmail()%></td>
        <td><button onclick="deleteRow(this)">삭제</button></td>
      </tr>
      <% } %>
    </tbody>
  </table>
  
  <button type = "submit" onclick="addRow()">행 추가</button>
  <button type = "submit" onclick="saveRow()">행 저장</button>
  <br/>
  <button type = "submit" onclick="saveToXML()">XML로 저장</button>
  <button type = "sumbit" onclick="saveToDB()">DB에 저장 (브라우저 -> DB)</button>

</body>
</html>
