function getArticleDetails(articleId) {
    
    //TODO:
    axios.get(`articleDetail.do?id=${articleId}`)
        .then(response => {
            const article = response.data.article;
            displayArticleDetails(article);
        })
        .catch(error => {
            console.log(error);
            alert(error.response.data.statusDescription);
            window.location.href = error.response.headers.path;
        });
}

function displayArticleDetails(article) {
    const articleTitleElement = document.getElementById('articleTitle');
    const articleUserElement = document.getElementById('articleUser');
    const articleContentElement = document.getElementById('articleContent');

    articleTitleElement.textContent = article.title;
    articleUserElement.textContent = '작성자: ' + article.user;
    articleContentElement.textContent = article.content;
    
    // 게시물 삭제 버튼 추가
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