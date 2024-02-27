function checkUserInArticle(article) {
    console.log("글을 작성한 유저가 맞는지 확인");
    
    axios.get('')
        .then(response => {
            createDeleteBtn(article);
        })
        .catch(error => {
            console.log(error);
        });
}

// 게시물 삭제 버튼 추가
function createDeleteBtn(article) {
        // 게시물 삭제 버튼 추가
    const deleteButtonElement = document.getElementById('delete');
    const deleteButton = document.createElement('button');
    deleteButton.textContent = '게시물 삭제';
    deleteButton.addEventListener('click', function() {
        deleteArticle(article.pk);
    });
    deleteButtonElement.appendChild(deleteButton);
}

function getArticleDetails(articlePk) {
    
    //TODO:
    axios.get(`articleDetail.do?pk=${articlePk}`)
        .then(response => {
            displayArticleDetails(response.data);
        })
        .catch(error => {
            console.log(error);
            if (!error){
                alert(error.response.data.statusDescription);
                window.location.href = error.response.headers.path;
            s}
        });
}

function displayArticleDetails(article) {
    console.log(article);
    
    const articleTitleElement = document.getElementById('articleTitle');
    const articleUserElement = document.getElementById('articleUser');
    const articleContentElement = document.getElementById('articleContent');

    articleTitleElement.innerHTML = article.title;
    articleUserElement.innerHTML= '작성자: ' + article.user;
    articleContentElement.innerHTML= article.content;

    checkIfUserIsIdentical(article);
    
   // makeFile(article.file);
}



function makeFileURL(file){
    //TODO:
    const articleFileLinkElement = document.getElementById('articleFileLink');
        
    let decodedFile= atob(file);
    let blob = new Blob([decodedFile], { type: 'application/octet-stream' }); // Blob 객체 생성
    let fileURL = URL.createObjectURL(blob); // Blob URL 생성
    const fileName = '파일'; // 파일 이름 설정
    let fileLink = document.createElement('a');
    
    fileLink.href = fileURL;
    fileLink.download = fileName;
    fileLink.textContent = `파일 다운로드 (${file.name})`; // 다운로드 링크에 파일 이름 표시
    articleFileLinkElement.appendChild(fileLink);
    articleFileLinkElement.appendChild(document.createElement('br')); // 각 파일마다 줄 바꿈 추가
}



function deleteArticle(articleId) {
    
    //TODO:
    
    axios.post('deleteArticle.do', { id: articleId })
        .then(response => {
            // 게시물 삭제에 성공한 경우 추가적인 동작 수행
            console.log('게시물 삭제 성공');
            // 예를 들어, 삭제 후에 어떤 작업을 수행하거나 페이지를 새로고침할 수 있습니다.
        })
        .catch(error => {
            console.log(error);
            alert('게시물 삭제에 실패했습니다.');
        });
}

function checkIfUserIsIdentical(article) {
    console.log("글 작성자가 유저인지 확인");
    
        axios.post('userCheckSame.do', { pk: article.pk })
        .then(response => {
            loadSameUserBtn(response.data.isSameUser);
        })
        .catch(error => {
            console.log(error);
            console.log("작성자가 아닌 유저");
        });
}

function loadSameUserBtn(isSameUser) {
    if (isSameUser) {
        console.log("글을 작성한 유저 ");
        document.getElementById('deleteBtn').style.display = 'block';
        document.getElementById('updateBtn').style.display = 'block';
    }
}