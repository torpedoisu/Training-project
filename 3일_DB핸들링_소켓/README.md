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

### 3.2. DB Rollback

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



<br>



### 3.3. 커밋 옵션과 트랜잭션 격리 수준



#### Read Uncommitted

- 트랜잭션에서 처리 중인, 아직 커밋되지 않은 데이터를 다른 트랜잭션이 읽는 것을 허용
- Dirty Read, Non-Repeatable Read, Phantom Read 현상 발생
- **Oracle은 이 레벨을 지원하지 않음**



<br>



#### Read Committed

- Dirty Read 방지 : 트랜잭션이 커밋되어 확정된 데이터만 읽는 것을 허용

- Non-Repeatable Read, Phantom Read 현상 발생

- **오라클 같은 DBMS가 기본모드로 채택하고 있는 격리 수준**

- **Oracle은 Lock을 사용하지 않고 쿼리시작 시점의 Undo 데이터를 제공하는 방식으로 구현**

`ReadCommitedTest.java`
  

![image](https://github.com/torpedoisu/Training-project/assets/157687140/7cecfab9-d884-474a-9e6a-3f923bd128af)


<br>



#### Repeatable Read

- 선행 트랜잭션이 읽은 데이터는 트랜잭션이 종료될 때가지 후행 트랜잭션이 갱신하거나 삭제하는 것을 불허함으로써 같은 데이터를 두 번 쿼리했을 때 일관성 있는 결과를 리턴
- Phantom Read 현상 발생
- **MySQL에서 기본으로 사용되는 격리 수준 (락 사용하지 않으면 언두 로그 사용)**
- Oracle은 이 레벨을 명시적으로 지원하지 않지만 for update 절을 이용해 구현가능



<br>



#### Serializable Read

- 가장 엄격한 격리 수준, 트랜잭션을 순차적으로 진행시킴
- 신형 트랜잭션이 읽은 데이터를 후행 트랜잭션이 갱신하거나 삭제하지 못할 뿐만 아니라 중간에 새로운 레코드를 산입하는 것도 막아주는 격리 수준 -> 여러 트랜잭션이 동일한 레코드에 동시 접근할 수 없으므로 어떤 데이터 부정합 문제도 발생하지 않음
- 가장 안전하지만 트랜잭션이 순차적으로 처리되어야 하므로 동시 처리 성능이 매우 떨어짐
`SerializableTest.java`

![image](https://github.com/torpedoisu/Training-project/assets/157687140/7e1aac70-2362-426b-833d-523040ea0773)



<br>



#### Java Docs

[Connection (Java Platform SE 8 )](https://docs.oracle.com/javase/8/docs/api/java/sql/Connection.html#TRANSACTION_REPEATABLE_READ)

```
int TRANSACTION_NONE             = 0;

int TRANSACTION_READ_UNCOMMITTED = 1;

int TRANSACTION_READ_COMMITTED   = 2;

int TRANSACTION_REPEATABLE_READ  = 4;

int TRANSACTION_SERIALIZABLE  = 8;
```


