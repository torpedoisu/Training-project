# 3일차) DB핸들링, 소켓

<br>

## 1. 프로그램 설명

### 1.1. 3일차_DB핸들링

`ManageDB.java`

- 회사 주소록 구조의 DB 테이블 생성

- 데이터 Insert/Update/Select/Delete



### 1.2. 3일차_소켓

`SocketClient.java`, `SocketServer.java`

- Socket Client Server 간 데이터(파일) 송수신 처리



<br>



## 2. 신경 쓸 부분

1. DB 조작 시 rollback, commit 옵션 생각하며 코드 짜기



<br>



## 3. 관련 정보

### 3.1. PreparedStatement와 Statement 객체



- SQL문을 실행할 수 있는 객체
- **가장 큰 차이점은 캐시 사용 여부**



<br>



#### Statement 객체

- 쿼리를 수행 할때마다 SQL 실행 단계 1~3을 거침
- SQL 문을 수행하는 과정에서 매번 컴파일을 하기 때문에 성능 이슈
- 실행되는 SQL문 확인 가능

```java
String sqlstr = "SELECT name, memo FROM TABLE WHERE name =" + num;
Statement stmt = conn.createStatement();
ResultSet rst = stmt.executeQuery(sqlstr);
```



<br>



#### Prepared Statement 객체

- 객체를 생성하는 시점에 쿼리 문장 분석과 컴파일을 처리하고 쿼리 혹은 커맨드 객체 생성 → 컴파일이 미리 되어 있기 때문에 Statement에 비해 좋은 성능
- **SQL 문자열을 키, 쿼리 객체 값으로** 내부 메모리 캐시에 저장해놓음
  - 쿼리 실행하는 `execute`, `executeQuery` 메소드 호출 시 캐시에 저장된 쿼리 객체 꺼내서 사용
- 특수문자를 자동으로 파싱해주기에 SQL injection 같은 공격을 막을 수 있음
- ? 부분에만 변화를 주어 쿼리문 수행하므로 실행되는 SQL문 파악 어려움

```java
String sqlstr = "SELECT name, memo FROM TABLE WHERE num = ?"
PreparedStatement stmt = conn.preparedStatement();
stmt.setInt(1, num);
ResultSet rst = stmt.executeQuery(sqlstr);
```



<br>



#### Statement과 PreParedStatement의 가장 큰 차이

- Statement사용하면 매번 쿼리 수행할 때마다 계속적으로 단계 거치면서 수행하지만 PreparedStatement는 처음 한 번만 [쿼리 문장 분석 -> 컴파일 -> 실행] 거친 후 캐시에 담아 재사용함

- 동일 쿼리 반복적으로 수행하면 PreparedStatement가 Db에 훨씬 적은 부하를 주며 성능도 좋음



<br>



#### PreparedStatement 객체를 사용 해야하는 경우

1. 사용자 입력값으로 쿼리 실행하는 경우
   - 특수 기호 들어와도 알아서 파싱해줌으로 SQL injection 막을 수 있음
2. 쿼리 반복 수행할 경우
   - 캐시에 저장해서 사용하기에 효율 좋음



<br>



### 3.2. Rollback 테스트

#### 트랜잭션 흐름

1. 트랜잭션 시작 (Begin Transaction)

2. 데이터베이스 작업 수행 (Data Manipulation)

3. 작업의 성공 여부에 따라 롤백 또는 커밋 결정
   
   - 성공한 경우: 커밋 (Commit)
   
   - 실패한 경우: 롤백 (Rollback)

4. 트랜잭션 종료 (End Transaction)



<br>



#### 커밋과 롤백

- **트랜잭션 내의 작업들이 모두 성공적으로 완료된 이후 데이터베이스에 영구적으로 반영(커밋), 그렇지 않은 경우 롤백을 통해 이전 상태로 되돌려야 함 (트랜잭션 초기화)**

- 트랜잭션이 성공적으로 모든 작업을 완료한 경우, 커밋을 수행하여 변경된 데이터를 영구적으로 저장



<br>



#### Rollback 테스트(`RollbackTest.java`)

![image-resize todoist](https://github.com/torpedoisu/Training-project/assets/157687140/ea961628-5ec4-4772-b036-f4b9c7e4a49a)

![image](https://github.com/torpedoisu/Training-project/assets/157687140/342eb4c3-53f5-4c2d-bc0d-62aa3a80e4be)





