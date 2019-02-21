import org.junit.Before;
import org.junit.Test;

/* 테스트 클래스의 이름에는 Test라는 접두어를 붙인다
* 빌드 스크립트에서 테스트를 조직하거나 필터링하기 쉬워진다. */
public class TestDefaultController {
    private DefaultController controller;

    // DefaultController 인스턴스를 만들기 위함.
    // @Before 메서드는 각 테스트 메서드 사이에서 호출되는 JUnit의 기본 확장 포인트.
    @Before
    public void instantiate() throws Exception{
        controller=new DefaultController();
    }

    // 실행할 테스트 케이스가 하나도 없으면 안되므로 dummy테스트 메서드를 추가
    @Test
    public void testMethod(){
        // 구현이 덜 끝난 테스트 코드는 예외를 던져야 한다는 모범사례를 따른다.
        throw new RuntimeException("implement me");
    }

}
