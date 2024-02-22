function login(event) {
    console.log("login 시작");
    
    const id = document.getElementById('id').value;
    const pwd = document.getElementById('pwd').value;
    console.log(id);
    // Axios를 사용하여 로그인 폼 제출
    axios.post('userLogin.do', { id, pwd })
        .then(response => {
            // 성공한 경우
            console.log(response.data);
        })
        .catch(error => {
            alert(error.response.data.statusDescription);
        });
}
