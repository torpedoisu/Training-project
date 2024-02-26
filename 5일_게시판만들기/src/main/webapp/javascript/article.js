function getArticleDetails(articleId) {
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
}