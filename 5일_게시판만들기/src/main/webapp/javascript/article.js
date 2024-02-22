function redirectToPost() {
    console.log("게시글 작성 페이지로 redirect");
    window.location.href = "post.jsp";
}

function checkUserInSession() {
    console.log("유저 로그인 상태인지 확인");
    const user = sessionStorage.getItem('user');
    if (!user) {
        window.location.href = "login.jsp";    
    }
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
    fileInput.value = ''; // 파일 선택을 초기화합니다.
    updateFileList(fileInput.files); // 파일 선택이 변경된 것을 반영합니다
}

function registerArticle() {
    const form = document.getElementById('articleForm');
    const formData = new FormData(form);

    axios.post('articleRegister.do', formData)
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