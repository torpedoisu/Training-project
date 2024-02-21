
function submitFile(e) {
    e.preventDefault;
    
    let fileInput = document.getElementById('fileUploader');
    let file = fileInput.files[0];
    
    console.log(file);

    if (!file) {
        return;
    }

    if (file.type !== 'text/xml') {
        return;
    }
    e.submit;
}
