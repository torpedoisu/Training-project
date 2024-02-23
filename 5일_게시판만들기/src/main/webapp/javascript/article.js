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



