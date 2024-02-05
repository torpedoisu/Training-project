<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>File Upload</title>
</head>
<body>

    <form action="AddressBook" method="post">
		<button id="openFile">파일 업로드</button>
	    <input type="file" id="fileUploader" style="display: none" />
    </form>


    <script>
        document.getElementById('openFile').addEventListener('click', function(){
            document.getElementById('fileUploader').click();
        });

        document.getElementById('fileUploader').addEventListener('change', function(e){
        	
        	console.log("파일 업로드 확인 - " + e);
            var file = e.target.files[0];
            if(file.type !== "text/xml"){
                alert("XML 파일을 업로드해주세요.");
            } else {
                var reader = new FileReader();
                reader.onload = function(e) {
                    var contents = e.target.result;
                    var parser = new DOMParser();
                    var xmlDoc = parser.parseFromString(contents, "text/xml");
                    console.log(xmlDoc);
                };
                reader.readAsText(file);
            }
        });
        
        
    </script>
</body>
</html>