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



<br>



## 3.2. 서블릿과 JSP



## 
