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



## 1.1. 파일 구조



<br>



## 1.2. 실행 환경

- `Java` jdk1.8

- `WAS` Tomcat 8.5

- `DB` Oracle

- 외부 라이브러리
  
  - Log4J
  
  - Lombok



<br>



## 1.3. 문서

### 1.3.1. 요구사항 명세

1. 사용자 로그인 기능
   
   - 사용자는 자신의 아이디와 비밀번호를 입력하여 로그인할 수 있어야 한다.
   - 아이디나 비밀번호를 잘못 입력한 경우, 잘못 입력하였음을 알리는 메시지를 보여주고 다시 로그인 할 수 있는 창을 보여줘야 한다.
   - 제한 사항
     - 사용자의 아이디는 10자로 제한한다
     - 사용자의 비밀번호는 20자로 제한한다

2. 사용자 로그아웃 기능
   
   - 사용자는 로그아웃 할 수 있어야 한다.

3. 게시물 등록 기능
   
   - 사용자는 제목, 내용을 입력하여 게시물을 등록할 수 있어야 한다.
   - 파일 첨부 기능을 통해 사용자는 사진, 문서 등의 파일을 게시물에 첨부할 수 있어야 한다.
   - 제한 사항
     - 제목은 50자 이내로 작성하게 한다.

4. 게시물 수정 기능
   
   - 사용자는 자신이 작성한 게시물만 수정할 수 있어야 한다.
   - 제목, 내용, 첨부된 파일 등 게시물의 모든 정보를 수정할 수 있어야 한다.
   - 제한 사항
     - 제목은 50자 이내로 작성하게 한다.

5. 게시물 삭제 기능
   
   - 사용자는 자신이 작성한 게시물만 삭제할 수 있어야 한다.

6. 게시물 보기 기능
   
   - 사용자는 게시물을 클릭하여 **제목, 내용, 첨부된 파일**을 볼 수 있어야 한다.
     
     - 사용자는 첨부된 파일을 다운로드 받을 수 있어야 한다.
   
   - 사용자는 게시물을 클릭하여 자세히 보기 전에 **게시물의 번호, 제목, 작성자**를 미리 볼 수 있어야 한다.



<br>



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



### 1.3.4. 클래스 다이어그램

![클래스다이어그램](https://github.com/torpedoisu/Training-project/blob/main/5%EC%9D%BC_%EA%B2%8C%EC%8B%9C%ED%8C%90%EB%A7%8C%EB%93%A4%EA%B8%B0/assets/%ED%81%B4%EB%9E%98%EC%8A%A4_%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.jpg?raw=true)



<br>



### 1.3.5. 시퀀스 다이어그램

<br>

# 2. 신경 써야 하는 부분

<br>

# 3. 관련 정보
