function redirectToPost() {
    console.log("게시글 작성 페이지로 redirect");
    window.location.href = "post.jsp";
}

function redirectToArticle(articlePk) {
    console.log("게시글 상세 페이지로 redirect");
    window.location.href = `article.jsp?pk=${articlePk}`;
}

function loadIndexBtn(data) {
    console.log("인덱스 페이지 버튼 설정");
    
    let isExist = data.isExist;
    if (isExist) {
        document.getElementById('logoutButton').style.display = 'block';
        document.getElementById('loginButton').style.display = 'none';
        document.getElementById('registerButton').style.display = 'none';
        document.getElementById('postButton').style.display = 'block';
    } else {
        document.getElementById('logoutButton').style.display = 'none';
        document.getElementById('loginButton').style.display = 'block';
        document.getElementById('registerButton').style.display = 'block';
        document.getElementById('postButton').style.display = 'none';
    }
    
}

function displayArticles(data) {
    console.log("게시글 목록 불러오기");
    
    let articlesArr = data.articles;
    
    // 테이블 요소 가져오기
    let table = document.getElementById("articleTableBody");

    // 기존 테이블 내용 비우기
    table.innerHTML = "";
    
    // 게시글 추가
    articlesArr.forEach(article => {
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

