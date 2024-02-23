
function registerPost() {
    console.log('게시글 작성');
    let title = document.getElementById('title').value;
    let content = document.getElementById('content').value;
    let fileList = document.getElementById('file').files;
    
    const formData = new FormData();
    
    formData.append('title', title);
    formData.append('content', content);
    
    
    for (let i = 0; i < fileList.length; i++) {
        formData.append('file' + i, fileList[i], fileList[i].name);
    }
    
    // formData에 잘 추가되었는지 확인
    for (const pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }
    
    axios.post('articleRegister.do', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
        .then(response => {
            console.log(response);
            window.location.href = response.headers.path;
        })
        .catch(error => {
            console.log(error);
            alert(error.response.data.statusDescription);
            window.location.href = error.response.headers.path;
        });
}

function convertFileToBlob(file) {
    return new Promise(resolve => {
        const reader = new FileReader();
        reader.onload = () => {
            const blob = new Blob([reader.result], { type: file.type });
            resolve(blob);
        };
        reader.readAsArrayBuffer(file);
    });
}

function updateFileList(files) {
    let fileList = document.getElementById('fileList');
    
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        
        const listItem = document.createElement('li');
        listItem.textContent = file.name;
        
        const removeButton = document.createElement('button');
        removeButton.textContent = '취소';
        
        removeButton.onclick = function() {
            removeFile(listItem);
        };
        
        listItem.appendChild(removeButton);
        fileList.appendChild(listItem);
    }
}

function removeFile(listItem) {
    const fileList = document.getElementById('fileList');
    const fileInput = document.getElementById('file')
    fileList.removeChild(listItem);
    fileInput.value = ''; 
    updateFileList(fileInput.files);
}