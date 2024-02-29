# 5일차) 게시판만들기

<br>

# 1. 프로그램 설명

- 사용자 로그인 기능

- 게시물 등록/수정/삭제/보기 기능 (파일 첨부 기능 포함)

#### 지켜야 하는 프로그램 설계 절차

1. 요구사항 명세 작성 (기능 목록 정의 및 기술)

2. 화면 정의

3. DB 테이블 명세 작성

4. 클래스 다이어그램 및 시퀀스 다이어그램 작성

5. 구현

<br>

## 1.1. 프로젝트 파일 구조

### 1.1.1. Controller와 Model

#### 1.1.1.1. com.global 패키지

`UUIDFactory.java`

- DB의 PK 생성을 위한 클래스 

- 버전 1 규약을 따르는 UUID 생성

`DBManager.java`

- DB와의 연결, 연결해제, 커밋 옵션 설정, 커밋, 롤백  등의 기능을 정의한 클래스

`Encrypt.java`

- 비밀번호 DB에 저장시 SHA-256 해시 알고리즘을 사용하기 위해 만든 클래스

`HttpUtil.java`

- HTTP 요청 수행 시 공통적으로 수행되는 기능을 모듈화 한 클래스 

#### 1.1.1.2. com.exception 패키지

`CustomException.java`

- 특정 에러 상황에서 사용자 정의 예외 처리를 위해 만들어진 클래스

- 에러 상황 로깅, HTTP 상태코드 및 에러 발생 후 리다이렉트 할 경로 정보 관리 

`ErrorHandleServlet.java`

- 사용자 정의 예외 발생 시 에러를 한 곳으로 모아 처리하고 사용자에게 통일된 형식의 에러 응답 데이터를 전송하기 위해 만든 클래스

- 일종의 예외에 대한 필터로 작동

#### 1.1.1.3. com.controller 패키지

`FrontController.java`

- 프론트 컨트롤러 패턴 구현

- 클라이언트 요청을 단일 진입점으로 받아들여 서브 컨트롤러로 라우팅 

`Controller.java`

- 서브 컨트롤러가 구현해야 하는 규약을 정의한 인터페이스

- 구현체
  
  - `ArticleDetailController.java`, `ArticleDeleteController.java`, `ArticleEditController.java`, `ArtricleRegisterContoller.java`, `ArticlesLoadController.java`, `UserAuthInIndexController.java`, `UserAuthInPostController.java`, `UserCheckSameController.java`, `UserLoginController.java`, `UserLogoutController.java`, UserRegisterController.java` 

#### 1.1.1.4. com.service 패키지

- `ArticleFileService.java`, `ArticleService.java`, `UserService.java`

#### 1.1.1.5. com.dao 패키지

- `ArticleDAO.java`, `ArticleFileDAO.java`, `UserDAO.java`

#### 1.1.1.6. com.vo 패키지

- `ArticleFileVO.java` , `ArticleVO.java`, `UserVO.java`

</br>

### 1.1.2. View

#### 1.1.2.1. jsp

- `editArticle.jsp`,  `login.jsp`

- `article.jsp` 상세 게시글 보기 페이지 (단일)

- `userRegister.jsp` 회원 가입 페이지

- `index.jsp` 게시글 목록 페이지

- `post.jsp` 게시글 수정 페이지

#### 1.1.2.2. javascript

- `article.js`, `editArticle.js`, `index.js`, `login.js`, `post.js`, `userRegister.js`

- AC 파일 사용하여 페이지 로드시 필요한 로직을 모듈화
  
  - `articleAC.js`,` editArticleAC.js`,` indexAC.js`,  `postAC.js`

<br>

## 1.2. 실행 환경

- `Java` jdk1.8

- `WAS` Tomcat 8.5

- `DB` Oracle

- 외부 라이브러리
  
  - Log4J

<br>

## 

## 1.3. 문서

### 1.3.1. 요구사항 명세

1. 사용자 로그인 기능
   
   - 사용자는 자신의 아이디와 비밀번호를 입력하여 로그인할 수 있어야 한다.
   - 아이디나 비밀번호를 잘못 입력한 경우, 잘못 입력하였음을 알리는 메시지를 보여주고 다시 로그인 할 수 있는 창을 보여줘야 한다.

2. 사용자 로그아웃 기능
   
   - 사용자는 로그아웃 할 수 있어야 한다.

3. 게시물 등록 기능
   
   - 사용자는 제목, 내용을 입력하여 게시물을 등록할 수 있어야 한다.
   - 파일 첨부 기능을 통해 사용자는 사진, 문서 등의 파일을 게시물에 첨부할 수 있어야 한다.

4. 게시물 수정 기능
   
   - 사용자는 자신이 작성한 게시물만 수정할 수 있어야 한다.
   - 제목, 내용, 첨부된 파일 등 게시물의 모든 정보를 수정할 수 있어야 한다.

5. 게시물 삭제 기능
   
   - 사용자는 자신이 작성한 게시물만 삭제할 수 있어야 한다.

6. 게시물 보기 기능
   
   - 사용자는 게시물을 클릭하여 **제목, 내용, 첨부된 파일**을 볼 수 있어야 한다.
     
     - 사용자는 첨부된 파일을 다운로드 받을 수 있어야 한다.
   
   - 사용자는 게시물을 클릭하여 자세히 보기 전에 **작성자, 본문, 제목을** 미리 볼 수 있어야 한다.
   
   - 

<br>

### 

### 1.3.4. 화면 정의

로그인 화면

<img src="https://github.com/torpedoisu/Training-project/blob/main/5%EC%9D%BC_%EA%B2%8C%EC%8B%9C%ED%8C%90%EB%A7%8C%EB%93%A4%EA%B8%B0/assets/%ED%99%94%EB%A9%B4%EC%A0%95%EC%9D%98_%EC%9D%B4%EB%AF%B8%EC%A7%80/%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%99%94%EB%A9%B4.png?raw=true" title="" alt="" width="464">

<br>

게시판 화면

<img src="https://github.com/torpedoisu/Training-project/blob/main/5%EC%9D%BC_%EA%B2%8C%EC%8B%9C%ED%8C%90%EB%A7%8C%EB%93%A4%EA%B8%B0/assets/%ED%99%94%EB%A9%B4%EC%A0%95%EC%9D%98_%EC%9D%B4%EB%AF%B8%EC%A7%80/%EA%B2%8C%EC%8B%9C%ED%8C%90%ED%99%94%EB%A9%B4.png?raw=true" title="" alt="" width="470">

<br>

게시글 화면 (작성자 본인 글)

<img src="https://github.com/torpedoisu/Training-project/blob/main/5%EC%9D%BC_%EA%B2%8C%EC%8B%9C%ED%8C%90%EB%A7%8C%EB%93%A4%EA%B8%B0/assets/%ED%99%94%EB%A9%B4%EC%A0%95%EC%9D%98_%EC%9D%B4%EB%AF%B8%EC%A7%80/%EA%B2%8C%EC%8B%9C%EA%B8%80%ED%99%94%EB%A9%B4_%EC%9E%91%EC%84%B1%EC%9E%90%EB%B3%B8%EC%9D%B8.png?raw=true" title="" alt="" width="484">

<br>

게시글 화면 (타인 글)

<img src="https://raw.githubusercontent.com/torpedoisu/Training-project/main/5%EC%9D%BC_%EA%B2%8C%EC%8B%9C%ED%8C%90%EB%A7%8C%EB%93%A4%EA%B8%B0/assets/%ED%99%94%EB%A9%B4%EC%A0%95%EC%9D%98_%EC%9D%B4%EB%AF%B8%EC%A7%80/%EA%B2%8C%EC%8B%9C%EA%B8%80%ED%99%94%EB%A9%B4_%EC%9E%91%EC%84%B1%EC%9E%90%ED%83%80%EC%9D%B8.png" title="" alt="" width="488">

<br>

### 1.3.3. 데이터베이스 테이블 설계

![데이터베이 테이블 설계](https://github.com/torpedoisu/Training-project/blob/main/5%EC%9D%BC_%EA%B2%8C%EC%8B%9C%ED%8C%90%EB%A7%8C%EB%93%A4%EA%B8%B0/assets/Db%ED%85%8C%EC%9D%B4%EB%B8%94_%EC%84%A4%EA%B3%84.png?raw=true)

<br>

### 

### 1.3.4. 클래스 다이어그램

![클래스다이어그램](https://github.com/torpedoisu/Training-project/blob/main/5%EC%9D%BC_%EA%B2%8C%EC%8B%9C%ED%8C%90%EB%A7%8C%EB%93%A4%EA%B8%B0/assets/%ED%81%B4%EB%9E%98%EC%8A%A4_%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png?raw=true)

<br>

<br>

# 2. 신경 써야 하는 부분

1. DB의 컬럼 이름 pk가 아닌 no, count, uuid 등으로 설정

2. axios 요청은 ac 파일을 거쳐서 하도록 하기 (회사 컨벤션)

3. 서버가 뜰 때 에러나는 부분은 반드시 해결

4. 프론트 컨트롤러 패턴 사용시 res, req는 클라이언트 요청당 발생하기에 서블릿안에서 전역적으로 사용 가능
