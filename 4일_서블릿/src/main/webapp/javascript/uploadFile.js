
function fileUploadSetup() {
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
}
