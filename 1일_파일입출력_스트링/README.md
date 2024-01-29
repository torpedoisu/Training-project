# 1일차) 파일 입출력, 스트링 문자열 변환



<br>



## 프로그램 설명

### 1일차_파일 입출력

```copyFile.java```

- 파일/디렉터리를 복사(copy) 하는 프로그램 작성

- input: src file명(디렉터리), dst file명(디렉터리)

`moveFile.java`

- 파일/디렉터리를 이동(move)시키는 프로그램 작성

- input: src file명(디렉터리), dst file명(디렉터리)



<br>



### 1일차_String 처리

`ReplaceString.java`

- text 문자열에서 특정 문자열을 치환하는 프로그램 작성

- input: 원본 text 문자열, 치환 대상 문자열, 치환 할 문자열

- output: 변경된 text 문자열

`ReplaceStringAndStore.java`

- text 파일 경로를 입력 받아서 파일의 특정 문자열을 치환하고 다른 이름의 파일로 저장하는 프로그램 작성

- input: 원본 text 파일명, 치환 후 저장될 text 파일 명, 치환 대상 문자열, 치환 할 문자열

`ChangeEncoding.java`

- text 인코딩을 UTF-8로 변경하는 프로그램 작성

- input: text 파일명

- output: 변환될 text 파일명

`ChangeDirEncoding.java`

- 특정 디렉터리 하위에 있는 모든 파일(혹은 디렉토리, nested 구조 지원)을 입력 받은 인코딩 포맷으로 변경하는 프로그램 작성

- input: 디렉토리명, 변환 인코딩 포맷



<br>



## 신경 쓸 부분

1. finally를 잘 사용하기
   
   - 선언과 초기화를 분리해서 선언한 객체가 null이 아닌 경우 stream close
   
   - stream 관련 프로그램이 아니더라도 try-catch문 사용할 때는 finally를 잘 쓰도록 노력 

2. 예외 처리를 자세하게 
   
   - throws로 던지지 말고 하나의 완전한 프로그램을 만든다는 생각 
   
   - 각 메서드마다 어떤 예외가 있는지 확인하고 고려하며 예외를 처리

3. 로그는 자세하게
   
   - 프로그램이 어떻게 돌아가는지 알기 위한 프로그램 시작, 중간 디버깅, 작업 완료, 예외, 등 로그 찍기

4. static은 객체를 생성하지 않아도 메모리에 고정적으로 할당이 되고, GC의 관리를 받지 않기 때문에 명확한 이유를 갖고 사용하는 것이 좋다
   
   - 만약 static 메서드 사용한다 가독성을 위해 앞에 객체명 적어주기



<br>



## 관련 정보

1. FileReader/FileWrtier 객체는 문서의 인코딩 방식이 아니라 시스템의 인코딩 방식을 받아와서 사용 (FileInputStream/FileOutputStream 객체는 문서 인코딩 방식 사용)

2. Stream 관련 객체에는 Decorator 디자인 패턴이 사용됨

### 


