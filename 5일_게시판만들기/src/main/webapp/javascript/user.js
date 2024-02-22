
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

function loadIndexButtons() {
    const user = sessionStorage.getItem('user');

    console.log(user);
    if (user) {
        document.getElementById('logoutButton').style.display = 'block';
        document.getElementById('loginButton').style.display = 'none';
        document.getElementById('postButton').style.display = 'block';
    } else {
        document.getElementById('logoutButton').style.display = 'none';
        document.getElementById('loginButton').style.display = 'block'
        document.getElementById('postButton').style.display = 'none';
    }
}

function logout() {
        axios.post('userLogout.do')
       .then(response => {
            console.log(response);
            window.location.href = response.headers.path;
        })
        .catch(error => {
            console.log(error);
            alert(error.response.data.statusDescription);
            window.location.href = error.response.headers.path;
        });
    sessionStorage.removeItem('user');
    checkLoginStatus();
    

}