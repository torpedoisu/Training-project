<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="servletpj.Employee" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Address Book</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<table id="addressBook">
        <tr>
            <th>부서</th>
            <th>이름</th>
            <th>직책</th>
            <th>영문이름</th>
            <th>휴대폰</th>
            <th>메일주소</th>
            <th></th>
        </tr>
        <% 
            List<Employee> employees = (ArrayList<Employee>) request.getAttribute("employees");
            for(Employee employee : employees) { 
        %>
        <tr>
            <td><%= employee.getDepartment()%></td>
            <td><%= employee.getName()%></td>
            <td><%= employee.getPosition()%></td>
            <td><%= employee.getEnglishName()%></td>
            <td><%= employee.getPhoneNumber()%></td>
            <td><%= employee.getEmail()%></td>
            <td><button class="deleteRow">삭제</button></td>
        </tr>
        <% } %>
    </table>
    <button id="addRow">행 추가</button>
    
    <script>
        $(document).ready(function(){
            $("#addRow").click(function(){
                var markup = "<tr><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><button class='deleteRow'>삭제</button></td></tr>";
                $("#addressBook").append(markup);
            });
            $(document).on("click", ".deleteRow", function(){
                $(this).closest('tr').remove();
            });
        });
    </script>
</body>
</html>