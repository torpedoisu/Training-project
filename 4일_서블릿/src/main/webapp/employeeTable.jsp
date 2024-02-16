<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="servletpj.EmployeeDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Address Book</title>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
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
                    List<EmployeeDAO> employees = (ArrayList<EmployeeDAO>) request.getAttribute("employees");
                    for(EmployeeDAO employee : employees) {
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
    <button id="saveRow">행 저장</button>
    <br/>
    <button id="saveToXML">XML로 저장</button>
    <button id="saveToDB">DB에 저장 (브라우저 -> DB)</button>
    
    <script language="JavaScript">
        
        $(document).ready(function(){
            var employees = []
            // 행 추가
            $("#addRow").click(function(){
                console.log("행 추가");
                var markup = "<tr id='row" + employees.length + "'><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><button class='deleteRow'>삭제</button></td></tr>";
                $("#addressBook").append(markup);
            });

            // 행 저장
            $("#saveRow").click(function(){
                console.log("행 저장");
                
                var newEmployee = {
                    department: $("#addressBook tr:last td:eq(0) input").val(),
                    name: $("#addressBook tr:last td:eq(1) input").val(),
                    position: $("#addressBook tr:last td:eq(2) input").val(),
                    englishName: $("#addressBook tr:last td:eq(3) input").val(),
                    phoneNumber: $("#addressBook tr:last td:eq(4) input").val(),
                    email: $("#addressBook tr:last td:eq(5) input").val(),
                    id: 'row' + employees.length // 행의 id를 저장합니다.
                    };
                employees.push(newEmployee);
                
                $("#addressBook tr:last td:eq(0)").text(newEmployee.department);
                $("#addressBook tr:last td:eq(1)").text(newEmployee.name);
                $("#addressBook tr:last td:eq(2)").text(newEmployee.position);
                $("#addressBook tr:last td:eq(3)").text(newEmployee.englishName);
                $("#addressBook tr:last td:eq(4)").text(newEmployee.phoneNumber);
                $("#addressBook tr:last td:eq(5)").text(newEmployee.email);
                
            });
               
            // 행 삭제
            $(document).on("click", ".deleteRow", function(){
                console.log("행 삭제");
                var id = $(this).closest('tr').attr('id'); 
                employees = employees.filter(function(employee) {
                    return employee.id !== id;
                    });
                $(this).closest('tr').remove();
                });

            // XML로 저장
            $("#saveToXML").click(function(){
                var employees = [];
                $("#addressBook tr:gt(0)").each(function() {
                    var department = $(this).find("td:eq(0)").text();
                    var name = $(this).find("td:eq(1)").text();
                    var position = $(this).find("td:eq(2)").text();
                    var englishName = $(this).find("td:eq(3)").text();
                    var phoneNumber = $(this).find("td:eq(4)").text();
                    var email = $(this).find("td:eq(5)").text();

                    var employee = {
                        department: department,
                        name: name,
                        position: position,
                        englishName: englishName,
                        phoneNumber: phoneNumber,
                        email: email
                    };
                    employees.push(employee);
                    
                });
                
                axios({
                    url: '/servlet/savetoxml',
                    method: 'POST',
                    responseType: 'blob',
                    data: {
                        employees: employees
                    }
                }).then(response => {
                        const blob = new Blob([response.data]);
                        var link = document.createElement('a');
                        link.href = window.URL.createObjectURL(blob);
                        link.download = "employees.xml";
                        link.click();
                    })
                    .catch(error => {
                        alert("다운로드 실패 - " + error.response.data.statusDescription);
                    });
            });
            
            // DB에 저장
            $("#saveToDB").click(function(){
                var employees = [];
                $("#addressBook tr:gt(0)").each(function() {
                    var department = $(this).find("td:eq(0)").text();
                    var name = $(this).find("td:eq(1)").text();
                    var position = $(this).find("td:eq(2)").text();
                    var englishName = $(this).find("td:eq(3)").text();
                    var phoneNumber = $(this).find("td:eq(4)").text();
                    var email = $(this).find("td:eq(5)").text();

                    var employee = {
                        department: department,
                        name: name,
                        position: position,
                        englishName: englishName,
                        phoneNumber: phoneNumber,
                        email: email
                    };
                    employees.push(employee);
                    
                });
                
                axios({
                    url: '/servlet/savetodb',
                    method: 'POST',
                    responseType: 'json',
                    data: {
                        employees: employees
                    }
                }).then(response => {
                        if (response.data.status == "SUCCESS") {
                            alert("DB에 저장 성공");
                        } else {
                            alert("DB에 저장 실패");
                        }
                    })
                    .catch(error => {
                        alert("DB에 저장 실패 - " + error.response.data.statusDescription);
                    });
            });
            
        });
        
        
    </script>
</body>
</html>
