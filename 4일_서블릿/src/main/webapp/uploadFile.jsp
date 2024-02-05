<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>File Upload</title>
</head>
<body>

    <form id="uploadForm" action="AddressBook" method="post" enctype="multipart/form-data">
	    <input type="file" name="file" id="fileUploader" />
   		<button id="sumbitFile">파일 제출</button>
    </form>


    <script language="JavaScript">
	    var fileUploaded = false;

        document.getElementById('fileUploader').addEventListener('change', function(e){
        	
        	console.log("파일 업로드 확인 - " + e);
        	
            var file = e.target.files[0];
            if(file.type !== "text/xml"){
                alert("XML 파일을 업로드해주세요.");
                document.getElementById('fileUploader').value = '';
            } else {
                fileUploaded = true;
            }
        });
        
        document.getElementById('submitFile').addEventListener('click', function(e){
            if(!fileUploaded){
                e.preventDefault();
                alert("XML 파일을 업로드해주세요.");
            } else {
                document.getElementById('uploadForm').submit();
            }
        });    
        
    </script>
</body>
</html>