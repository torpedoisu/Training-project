
function login() {
    console.log("login 시작");
    
    const id = document.getElementById('id').value;
    const pwd = document.getElementById('pwd').value;

    // Axios를 사용하여 로그인 폼 제출
    axios.post('userLogin.do', { id, pwd })
        .then(response => {
            console.log(response);
            sessionStorage.setItem("user", id);
            window.location.href = response.headers.path;
        })
        .catch(error => {
            console.log(error);
            alert(error.response.data.statusDescription);
            window.location.href = error.response.headers.path;
        });
}

function redirectToLogin() {
    console.log("로그인 페이지로 redirect");
    window.location.href = "login.jsp";
}

function redirectToRegister() {
    console.log("회원가입 페이지로 redirect");
    window.location.href = "userRegister.jsp";
}

function register() {
    console.log("register 시작");
    
    const id = document.getElementById('id').value;
    const pwd = document.getElementById('pwd').value;

    // Axios를 사용하여 로그인 폼 제출
    axios.post('userRegister.do', { id, pwd })
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

