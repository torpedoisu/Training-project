# 2일차) 스레드, XML 생성 및 파싱

<br>

## 프로그램 설명

### 2일차_스레드

**Producer-Consumer** 패턴 구현하는 프로그램 작성

`Consumer.java`

- Consumer Thread는 M개가 존재

- Consumer Thread는 Producer에 의해 생성된 Work 객체를 사용하고 모든 객체 소비 후 다시 객체를 얻기 위해 대기

`Producer.java`

- Producer Thread는 초기 N개의 Work 객체를 생성

- Producer Thread는 Work 객체를 5초에 한 개씩 생성

`Work.java`

- Work 객체는 execute()라는 메서드가 일을 하는 것이며 이 메서드는 [1-3]초 사이 임의의 시간 동안 sleep 하는 기능을 포함



<br/>



### 2일차_XML 생성 및 파싱

`CreateXML.java`

- DOM을 이용하여 XML 생성하는 프로그램 작성 (형식 자유)

`DOMParserForXML.java`

- CreateXML.java에서 생성한 XML을 DOM 파서를 이용해 파싱하는 프로그램 작성

`SAXParserForXML.java`

- CreateXML.java에서 생성한 XML을 SAX 파서를 이용해 파싱하는 프로그램 작성



<br/>



## 신경 쓸 부분

1. 새롭게 사용하는 자료구조나 기능은 개념적으로 이해 후 테스트

2. 사용한 JAVA API에 대한 이해를 확실히

3. JAVA API 사용 시 사용 목적을 명확히
   
   - 요구 사항의 어떤 부분 때문에 해당 객체, 메서드 사용한 것인지 인지



<br/>



## 관련 정보

### Blocking Queue

설명

- 특정 상황에 스레드를 대기하도록 하는 큐 인터페이스

- 인큐를 시도했는데 큐에 공간이 없을 때, 디큐시 큐가 비어있을 때 디큐/인큐 호출 스레드를 대기하도록 한다

- java.util.concurrent 패키지에 BlockingQueue 존재 [BlockingQueue (Java Docs)](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html)

- 구현체 - ArrayBlockQueue, LinkedBlockingQueue, SynchronousQueue



<br/>



사용 목적

- Consumer Thread는 Work 객체를 사용하고 모든 객체 소비 후 다시 객체를 얻기 위해 대기해야 하는 요구 조건을 충족하기 위함

- Producer Thread가 Work를 계속해서 생성하기에 큐의 크기가 동적인 LinkedBlockingQueue 구현체 사용



<br/>



Queue 인터페이스와의 차이

- 디큐 메서드
  
  - poll() - 큐가 비어있을 때 NullPointerException 예외 발생
  
  - take() - 큐가 비어있을 때 꺼낼 수 있는 엘리먼트가 생길 때 까지 스레드가 블록되어 대기

- 인큐 메서드
  
  - add() - 인큐 시 큐 용량이 가득 차 있으면 IllegalStateException 예외 발생
  
  - put() - 인큐 시 큐 용량이 가득 차 있으면 스레드가 블록되어 대기 

- 구현 예시
  
  ``` 
    public synchronized void enqueue(Object item)
    throws InterruptedException  {
      while(this.queue.size() == this.limit) {
        wait();
      }
      if(this.queue.size() == 0) {
        notifyAll();
      }
      this.queue.add(item);
    }
  ```



<br/>



비교를 위한 LinkedList 테스트

![image](![image-resize%20todoist](https://github.com/torpedoisu/Training-project/assets/157687140/086b7590-a906-4465-be08-16d06bf695de))





<br/>



### DOM과 SAX의 장단점



DOM 파싱 방식의 장점

- 전체 문서를 한 번에 로드하여 처리함으로 임의 요소에 대한 수정, 삭제, 추가 등의 작업 용이

DOM 파싱 방식의 단점

- 대용량 문서의 경우 모든 요소를 메모리에 로드한 이후 작업을 시작함으로 메모리 부하가 크고 성능이 저하 될 가능성

- 초기 로딩 시간이 문서 크기와 비례



<br/>



SAX 파싱 방식의 장점

- 이벤트 핸들러를 사용하여 동작함으로 메모리 사용량이 작고 속도가 빠름

- 문서를 한 번에 로드하지 않고 순차적으로 파싱하므로 대용량 문서에 대한 처리가 효율적

- 요소가 순차적으로 처리되기에 일부분만 필요한 경우 효율적인 사용이 가능

- 파싱 중 데이터 실시간 처리 가능

SAX 파싱 방식의 단점

- 전체 문서 구조 파악하기 어려움

- 순차적으로 처리되기에 특정 요소를 참조해야 하는 경우 추가적인 로직 필요




