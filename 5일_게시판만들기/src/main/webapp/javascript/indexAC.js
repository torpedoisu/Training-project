function processRequest(loader) {
    axios({
        method: loader.method,
        url: loader.url,
        // data: loader.data
    })
    .then(response => {
        console.log('요청을 성공적으로 처리하였습니다.');
        if (loader.callMethod) {
            window[loader.callMethod](response.data);
        }
    })
    .catch(error => {
        console.log('요청 수행 중 문제가 발생했습니다.');
        alert(error.response.data.statusDescription);
        window.location.href = error.response.headers.path;
    });
}