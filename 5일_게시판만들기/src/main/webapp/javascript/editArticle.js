
/*
    ========[초기화 함수]========
*/
function loadArticleDetails(article){
    document.getElementById('editTitle').value = article.title;
    document.getElementById('editContent').value = article.content;
}

function getfileDetailsForEdit(article) {
    console.log('파일 정보 가져오기');
            
    axios.get(`articleFileDetail.do?pk=${article.pk}`)
    .then(response => {
        const files = response.data.files;
        for (const fileTitle in files) {
            displayEditFileList(fileTitle, files[fileTitle]); // 파일 리스트 표시 로직
        }
    })
    .catch(error => {
        console.log(error);
    });
}

/*
    ========[초기화 함수에서 사용하는 함수]========
*/
// getfileDetailsForEdit에서 사용
function displayEditFileList(fileTitle, base64Data) {
    console.log('파일 목록에 파일 추가:', fileTitle);

    const fileListContainer = document.getElementById('editFileList');

    const fileListItem = document.createElement('li');
    fileListItem.textContent = fileTitle;

    // 삭제 버튼 생성
    const deleteButton = document.createElement('button');
    deleteButton.textContent = '삭제';

    // 삭제 버튼 클릭 이벤트 핸들러
    deleteButton.onclick = function() {
        fileListContainer.removeChild(fileListItem);
    };

    fileListItem.appendChild(deleteButton);
    fileListContainer.appendChild(fileListItem);
}

/*
    ========[버튼에 있는 함수들]========
*/
function updateFileList(files) {
    let fileList = document.getElementById('editFileList');
    
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        
        const listItem = document.createElement('li');
        listItem.textContent = file.name;
        
        const removeButton = document.createElement('button');
        removeButton.textContent = '삭제';
        
        removeButton.onclick = function() {
            removeFile(listItem);
        };
        
        listItem.appendChild(removeButton);
        fileList.appendChild(listItem);
    }
}

function saveArticleEdit(articlePk) {
    const title = document.getElementById('editTitle').value;
    const content = document.getElementById('editContent').value;
    const fileList = document.getElementById('editFile').files;

    const formData = new FormData();
    formData.append('title', title);
    formData.append('content', content);
    formData.append('pk', articlePk);
    
    // 수정된 파일 추가
    for (let i = 0; i < fileList.length; i++) {
        formData.append(fileList[i].name, fileList[i]);
    }

    axios.post('articleEdit.do', formData, {
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



