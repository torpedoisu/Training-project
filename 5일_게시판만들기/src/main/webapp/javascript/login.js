function login() {
    event.preventDefault();

    let id = document.getElementById("id").value;
    let pwd = document.getElementById("pwd").value;

    // 여기서 id와 pwd를 서버로 전송하여 로그인 처리를 수행할 수 있음
    // 예를 들어, fetch나 XMLHttpRequest를 사용하여 AJAX 요청을 보낼 수 있음
    // 이후 서버에서 로그인 결과에 따라 적절한 동작을 수행하면 됨
    // 이 예시에서는 AJAX 요청을 보내는 부분만 작성함
    // 실제 서버와의 통신을 위한 URL은 실제 환경에 맞게 수정해야 함

    // AJAX 요청 예시 (fetch를 사용하는 경우)
    fetch('loginServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: id, pwd: pwd })
    })
    .then(response => {
        if (response.ok) {
            // 로그인 성공 시의 동작
            alert('로그인 성공!');
            window.location.href = 'welcome.jsp'; // 로그인 성공 후 이동할 페이지로 리다이렉트
        } else {
            // 로그인 실패 시의 동작
            alert('로그인 실패. 아이디나 비밀번호를 확인해주세요.');
        }
    })
    .catch(error => {
        console.error('로그인 오류:', error);
    });
}

// 버튼에 이벤트 리스너 추가
document.getElementById("loginBtn").addEventListener("click", login);
