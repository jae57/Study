<p align="center">
  <a href="#"><img width="80" src="../../images/hand-puppet.svg"></a><br>
</p>

---

## JUnit 자세히보기
```java
@Before
@After
```
@Before과 @After 애노테이션이 부여된 메서드들은
매 @Test메서드가 호출되기 **바로 직전과 직후**에 실행된다.
@Test 메서드의 성공 실패 여부는 상관없다.

때문에 도메인 객체를 미리 생성해두거나,
특정 상태로 미리 셋팅하기 위한 공통 코드를 뽑아두기에 최적의 장소라 할 수 있다.

원한다면 여러 개의 @Before와 @After메서드를 정의할 수도 있지만,
이들 사이의 실행 순서를 정하는 방법은 없으니 주의할 것.
```java
@BeforeClass
@AfterClass
```
이들 애노테이션의 대상은 클래스다.
다시 말해 클래스 안에 정의된 모든 @Test 메서드들을 수행하기 전과 수행한 후에 오직 한 번씩만 호출된다.
이처럼 @BeforeClass와 @AfterClass메서드도 원한다면 여러 개 정의할 수 있지만, 실행 순서는 정의되어 있지 않다.


> @Before/@After, @BeforeClass/@AfterClass 메서드 모두는 **반드시 public**이어야 하며,
> @BeforeClass와 @AfterClass메서드는 **public이면서 동시에 static**이어야 한다.
