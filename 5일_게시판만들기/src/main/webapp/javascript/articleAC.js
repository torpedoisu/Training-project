function processRequest(loader) {
    axios({
        method: loader.method,
        url: loader.url,
        // data: loader.data
    })
    .then(response => {
        console.log('요청을 성공적으로 처리하였습니다.');
        
        if (typeof loader.callMethod == 'object') {
            loader.callMethod.forEach((doMethod) => {
                console.log('실행 - ', doMethod);
                // 전역적으로 메서드가 실행되었기에 전역 객체 window의 프로퍼티로 호출 가능
                window[doMethod](response.data);
            });
        }
    })
    .catch(error => {
        console.log('요청 수행 중 문제가 발생했습니다.');
        console.log(error);
        alert(error.response.data.statusDescription);
        window.location.href = error.response.headers.path;
    });
}