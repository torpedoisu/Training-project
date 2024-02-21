
const User = function(name="anonymous", age = 0){
    this.name = name;
    this.age = +age;
};

let path = window.location.pathname;
let lastSlashIndex = path.lastIndexOf('/');
let pathWithoutFileName = path.substring(0, lastSlashIndex);


const COMMON_SERVLET_URL = `${pathWithoutFileName}/common`;
const AXIOS_SERVLET_URL = `${pathWithoutFileName}/axios`;

// alert(ROOT_URL);

// jQuery의 ajax
function jQueryAjax() {
    alert("jQeryAjax 시작");
    
    let jQueryAjaxUser = new User("jQuery", 10);
        
    $.ajax({
        url: COMMON_SERVLET_URL,
        type: "POST",
        data: JSON.stringify(jQueryAjaxUser),
        contentType: "application/json;charset=utf-8",
        success: (data) => {
            console.log('data', data);
            alert('data - ' + data.name + ' ' + data.age);
        },
        error: (jqXHR, textStatus, errorThrown) => {
            console.error(textStatus, errorThrown);
            alert(textStatus+ ' ' + errorThrown);
        }
    });
}

//바닐라 js로 ajax 구현
function vanillaAjax() {
    alert("vanillaAjax 시작");
    
    let vanillaUser = new User("vanilla", 15);
    
    // 생성자 객체
    let xhr = new XMLHttpRequest();
    
//    for (key in xhr) {
//      console.log(key + xhr[key]);
//    }
    
    xhr.open("POST", COMMON_SERVLET_URL, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=utf-8");
    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log(JSON.parse(xhr.responseText));
            alert(xhr.responseText);
        } else {
            console.error('Error:' + xhr.status + xhr.statusText);
            alert('Error:' + xhr.status + xhr.statusText)
        }
    };
    xhr.onerror = function() {
        console.error('Network Error');
    };
    xhr.send(JSON.stringify(vanillaUser));
}

// axios의 json 자동 변환
function axiosJson() {
    alert("axiosJson 시작");
    
    
    let axiosUser = new User("axios", 20);
    
    axios.post(AXIOS_SERVLET_URL, axiosUser, {
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    })
    .then((response) => {
        console.log('response data', response.data);
        alert('response data - ' + response.data.name + ' ' + response.data.age);
    })
    .catch((error) => {
        console.error('error', error);
        alert('error - ' + error);
    });
    
}


 