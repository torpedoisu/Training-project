function getArticleDetails(articlePk) {
    
    //TODO:
    axios.get(`articleDetail.do?pk=${articlePk}`)
        .then(response => {
            const article = response.data;
            displayArticleDetails(article);
        })
        .catch(error => {
            
            alert(error.response.data.statusDescription);
            window.location.href = error.response.headers.path;
        });
}

function displayArticleDetails(article) {
    console.log(article);
    const articleTitleElement = document.getElementById('articleTitle');
    const articleUserElement = document.getElementById('articleUser');
    const articleContentElement = document.getElementById('articleContent');
    const articleFileLinkElement = document.getElementById('articleFileLink');

    articleTitleElement.innerHTML = article.title;
    articleUserElement.innerHTML= '작성자: ' + article.user;
    articleContentElement.innerHTML= article.content;

    // 파일(blob) 처리

    if (article.file) {
        // 파일이 있는 경우 파일 다운로드 링크 생성
        const fileURL = URL.createObjectURL(article.file); // Blob URL 생성
        const fileName = 'downloaded_file'; // 파일 이름 (임시로 설정)
        const fileLink = document.createElement('a');
        fileLink.href = fileURL;
        fileLink.download = fileName;
        fileLink.textContent = '파일 다운로드';
        articleFileLinkElement.appendChild(fileLink);
    }

    // 게시물 삭제 버튼 추가
    const deleteButtonElement = document.getElementById('delete');
    const deleteButton = document.createElement('button');
    deleteButton.textContent = '게시물 삭제';
    deleteButton.addEventListener('click', function() {
        deleteArticle(article.id);
    });
    deleteButtonElement.appendChild(deleteButton);
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

function checkIfUserIsIdentical() {
    console.log("글 작성자가 유저인지 확인");
    // TODO:
}