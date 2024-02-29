/*
    ========[초기화 함수]========
*/
function checkUserInPost() {
    console.log("유저 로그인 상태인지 확인");
    
    axios.get('userAuthInPost.do')
        .then(response => {
            console.log('유저 로그인 상태');
        })
        .catch(error => {
            console.log('유저 로그인 상태 아님');
            alert(error.response.data.statusDescription);
            window.location.href = error.response.headers.path;
        });
}

/*
    ========[버튼에 있는 함수들]========
*/
function registerPost() {
    console.log('게시글 작성');
    let title = document.getElementById('title').value;
    let content = document.getElementById('content').value;
    let fileList = document.getElementById('file').files;

    const formData = new FormData();
    
    formData.append('title', title);
    formData.append('content', content);
    
    for (let i = 0; i < fileList.length; i++) {
        formData.append(fileList[i].name, fileList[i]);
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

/*
    ========[버튼 함수가 사용하는 함수]========
*/
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
