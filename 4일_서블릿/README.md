# 4일차) 서블릿

<br>

# 1. 프로그램 설명

서블릿 엔진을 사용하여 요청 / 응답 처리

- XML 파일을 입력받고 입력받은 XML 파일 파싱 후 데이터를 표로 보여주기

- 회사 주소록 추가 및 삭제하는 화면 (추가 삭제는 JavaScript 사용하여 동적으로)

- 데이터 입력 후 해당 내용 XML 파일로 저장하기

- 데이터 입력 후 해당 내용 DB에 저장하기 (주소록 구조에 적합한 테이블 생성)



<br>



## 1.1. 파일 구조

1. JSP 관련 파일
- `uploadFile.jsp`
  
  - XML 파일을 업로드 하는 페이지

- `employeeTable.jsp`
  
  - axios 사용
  
  - Employee 정보를 표시하고, 추가 및 삭제를 할 수 있는 페이지

- `jqueryEmployeeTable.jsp`
  
  - jquery 사용
  
  - Employee 정보를 표시하고, 추가 및 삭제를 할 수 있는 페이지



<br>



2. 서블릿 관련 파일 (servletpj 패키지)
- `AddressBookServlet.java`
  
  - 파일 업로드 후 요청을 처리하는 서블릿
  
  - XMLParser를 사용하여 XML에서 값을 추출한 후, employeeTable.jsp로 전송

- `SaveToDBServlet.java`
  
  - DB에 Employee 정보를 저장하는 서블릿
  
  - DBManager를 사용하여 EmployeeDB에 Employee 객체를 저장

- `SaveToXMLServlet.java`
  
  - XML 파일로 Employee 정보를 저장하는 서블릿
  
  - XMLParser를 사용하여 XML 파일을 생성한 후, 브라우저로 다운로드할 수 있도록 함



<br>



3. DB 관련 파일 (db 패키지)
- `DBManager.java`
  
  - DB 커넥션을 관리하는 클래스
  
  - DB 접속, 해제, 롤백 등의 기능

- `EmployeeDB.java`
  
  - Employee 테이블을 조작하는 클래스
  
  - DBManager를 사용하여 Employee 테이블과 사용자의 데이터를 비교한 후 Employee 테이블을 업데이트 하는 기능



<br>



4. 기타
- `Employee.java` (db 패키지)
  
  - 데이터베이스의 Employee 테이블과 일대일 매핑하여 데이터를 읽고 쓸 수 있도록 함

- `XMLParser.java` (servletpj 패키지)
  
  - 파일 업로드 시 XML로 변환하거나, XML에서 값을 추출하는 유틸리티 클래스
  
  - 파일을 XML로 변환하거나, XML에서 값을 추출하여 사용 가능한 기능

- `ResponseData.java` (response 패키지)
  
  - 에러와 성공에 관련된 응답을 처리하는 클래스

- `Status.java` (response 패키지)
  
  - ResponseData에서 사용할 SUCCESS, FAIL을 Enum으로 정의한 클래스



<br>



5. 테스트
- `EmployTestXML.xml`
  
  - 다른 값을 가진 XML 

- `EmployTestXML2.xml`
  
  - 다른 값을 가진 XML

- `WrongTestXML.xml`
  
  - 형식을 지키지 않은 XML





<br>



## 1.2. 실행 환경

- `Java` jdk1.8

- `WAS` Tomcat 8.5

- `DB` Oracle





<br>



# 2. 신경 쓸 부분



<br>



# 3. 관련 정보

## 3.1. Ajax

### Ajax 특징

- 라이브러리가 아닌 Aysnchronous JavaScript and XML의 약자, JavaScript와 XML을 이용한 비동기적 정보 교환 기법
  - jQuery의 ajax는 라이브러리
  - 요즘은 JSON을 주로 사용
- 브라우저의 XMLHttpRequest 객체를 이용해 **전체 페이지를 새로 가져오지 않고도 페이지 일부만 변경할 수** 있도록 JavaScript를 실행해 서버에 데이터만을 별도로 요청하는 기법
  - 브라우저는 HTML, CSS를 이용해 골격을 먼저 형성하고 **ajax 실행부가 담긴 JavaScript 영역을 실행해 데이터를 별도로 가져와 끼워넣은 후** 페이지를 로딩
- **HTTP 프로토콜**을 사용한 **비동기 통신**
  - 비동기 통신 - 서버의 return을 기다리지 않고 다른 작업을 진행하는 방식

단점

- Ajax 방식으로 생성된 콘텐츠는 검색 엔진 크롤러가 동적으로 로드된 콘텐츠를 파싱하기 어려워 검색 엔진 최적화에 어려움을 겪을 수있음

 XMLHttpRequest (XHR)

- 브라우저에서 제공하는 web api
- XML 뿐만 아니라 모든 종류의 데이터를 받아오는데 사용할 수 있음
- HTTP 이외의 프로토콜도 지원 (file과 ftp 포함)
- XMLHttpRequest 는 Promise객체를 반환하는게 아니기에 async 와 결합하여 비동기 코드를 효율적으로 관리하기 어려움



<br>



### jQuery의 Ajax를 보완한 Axios가 제공하는 기능

- **구문의 간결성** - Axios는 Promise 기반이기에 XMLHttpRequest 객체를 직접 사용할 때 보다 비동기 처리에 대한 코드를 더 쉽게 작성 가능

- **에러 처리 향상** - Axios는 Promise 기반이기에 비동기 요청을 보낼 때 발생하는 에러를 간편하게 catch 할 수 있음

- **JSON 데이터 자동 변환** - JSON parsing이나 요청 데이터의 JSON stringify를 직접 할 필요가 없음

- **요청 및 응답 인터셉터** - **모든 HTTP 요청**이 전송되기 전이나 서버로부터 응답을 받기 전에 데이터를 처리할 수 있도록 인터셉터 기능 제공 (전역적)
  
  - axios 요청 인터셉터 처리 - 요청 보내기 전 실행 (전역)
    
    ```jsx
    // request interceptors 사용
    axios.interceptors.request.use((config) => {
        // request를 보내기 전에 사용
        // doSomething();
        return config;
    }, (error) => {
        // request가 error 났을 시
        return Promise.reject(error);
    });
    ```
  
  - axios 응답 인터셉터 처리 - 응답 받기 전 실행하고 이후 axios 실행 (전역)
    
    ```jsx
    axios.interceptors.response.use(
      response => {
        // 응답 데이터를 가공하여 반환
        return response.data;
      },
      error => {
        // 응답이 실패했을 때 에러 처리
        if (error.response.status === 401) {
          alert('로그인이 필요합니다.');
        }
        return Promise.reject(error);
      }
    );
    ```
  
  - jQuery ajax 요청 전 실행 메서드 (전역)
    
    ```jsx
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', `Bearer ${localStorage.getItem('token')}`);
        }
    });
    ```
  
  - jQuery ajax 응답 후 실행 메서드 - 모든 메서드에 직접 넣어줘야 함 (지역적)
    
    ```jsx
    $.ajax({
        url: '/api/path',
        type: 'GET',
        complete: function(xhr, textStatus) {
            if (xhr.status === 401) {
                alert('로그인이 필요합니다.');
            }
        }
    });
    ```

- 



<br>



### jQuery의 Ajax와 Axios의 차이

1. Promise 기반
   - jQuery의 Ajax는 Promise 기반이 아니며, 성공 또는 실패에 대한 처리를 콜백 함수로 직접 전달
   - Axios는 Promise를 사용하여 비동기 요청을 처리
     - **`.then()`** 및 `.catch()`와 같은 Promise 메서드를 사용하여 요청의 성공 또는 실패에 대한 처리
2. 자동 직렬화와 역직렬화
   - Axios는 기본적으로 JSON 객체를 전송할 때 객체를 자동으로 직렬화
   - jQuery Ajax는 기본적으로 JSON 객체를 문자열 그대로 전송하므로, 수동으로 JSON.stringify() 및 JSON.parse()를 사용하여 객체를 처리해야함



<br>



### submit과 ajax의 차이

1. 동기와 비동기
   - submit은 동기 방식으로 서버에서 return이 오기 전까지 다른 일을 수행할 수 없음
   - ajax는 비동기 방식으로 서버에서 return이 오기 전에도 다른 일을 할 수 있음
2. 페이지 새로고침
   - submit은 페이지 전체가 리로드 되기에 서버의 부하가 커질 수 있음
   - ajax는 전체 페이지를 새로고침 하지 않고 해당 페이지의 ajax 부분만 동적으로 리로드



<br>



## 3.2. 서블릿과 JSP



## 
