function redirectToPost() {
    console.log("게시글 작성 페이지로 redirect");
    window.location.href = "post.jsp";
}

function redirectToArticle(articlePk) {
    console.log("게시글 상세 페이지로 redirect");
    window.location.href = `articleDetail.jsp?id=${articleId}`;
}

function checkUserInSession() {
    console.log("유저 로그인 상태인지 확인");
    
    axios.get('userAuth.do')
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            console.log(error);
        });
}

function loadArticles(){
    console.log("게시글 로딩");
    
    axios.get('articlesLoad.do')
        .then(response => {
            console.log(response.data.articles);
            displayArticles(response.data.articles);
        })
        .catch(error => {
            console.log(error);
            //alert(error.response.data);
            //window.location.href = error.response.headers.path;
        });
}

function displayArticles(articles) {
    // 테이블 요소 가져오기
    let table = document.getElementById("articleTableBody");

    // 기존 테이블 내용 비우기
    table.innerHTML = "";
    
    // 게시글 추가
    articles.forEach(article => {
        let newRow = table.insertRow();
        
        let userCell = newRow.insertCell(0);
        let titleCell = newRow.insertCell(1);
        let contentCell = newRow.insertCell(2);
        let actionCell = newRow.insertCell(3); // 바로가기 셀 추가
        
        userCell.innerHTML = article.user;
        titleCell.innerHTML = article.title;
        contentCell.innerHTML = article.content;

        actionCell.innerHTML = `<button onclick="redirectToArticle(${article.pk})">상세보기</button>`; // 버튼 추가
    });
}

