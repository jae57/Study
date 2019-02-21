import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    /*@Test
    public void testMethod(){
        // 구현이 덜 끝난 테스트 코드는 예외를 던져야 한다는 모범사례를 따른다.
        throw new RuntimeException("implement me");
    }*/

    private class SampleRequest implements Request{
        public String getName(){
            // 약속된 이름(Test)를 반환하는 요청 객체를 만든다.
            return "Test";
        }
    }

    private class SampleHandler implements RequestHandler{
        // 인터페이스를 사용하는 측에서 process메서드를 호출할 것이므로, 구현해 넣었다.
        public Response process(Request request) throws Exception{
            // 바로 process 메서드를 테스트할 것이 아니므로, SampleResponse 객체를 반환하도록 하여
            // 시그니처만 만족시키도록 해둔다.
            return new SampleResponse();
        }
    }

    // 인스턴스화할 것을 준비해놓는다.
    private class SampleResponse implements Response{
    }

    /* 테스트 메서드의 이름은 가급적 직관적이어야 한다.
    * @Test 어노테이션을 부여하여 테스트 메서드임을 선언한다. */
    @Test
    public void testAddHandler(){
        // 먼저 테스트 객체들을 인스턴스화 해야한다.
        Request request = new SampleRequest();
        RequestHandler handler=new SampleHandler();

        // DefaultController객체(controller)는 @Before메서드에서 미리 인스턴스화 했음.
        // 컨트롤러(테스트 대상 객체)에 테스트 핸들러를 추가한다.
        controller.addHandler(request,handler);
        // 핸들러를 얻어 새 변수(handler2)에 할당한다.
        RequestHandler handler2 = controller.getHandler(request);
        // 앞서 추가했던 핸들러(handler)와 handler2가 동일한지 단언(assertion)해보자.
        assertSame("Handler we set in controller should be the same handler we get", handler2, handler);
    }

    /* JUnit의 assert* 메서드를 사용할 때는, 항상 첫 파라미터로 String을 받도록 하자.
    *  이 파라미터를 이용하면 assert실패 시 JUnit 테스트 러너가 테스터에게 의미있는 설명을 제공할 것이다. */
    @Test
    public void testProcessRequest(){
        // 기본 세팅
        // 테스트 객체들 준비하고 테스트 핸들러 추가해둔다
        Request request = new SampleRequest();
        RequestHandler handler = new SampleHandler();
        controller.addHandler(request,handler);

        // processRequest메서드를 호출한다.
        Response response = controller.processRequest(request);

        // 반환된 Response객체가 null이 아닌지 검사한다.
        // 뒤이어 Response객체의 getClass() 메서드를 호출해야 하므로 중요한 선행검사다.
        // 테스트가 싫패했을 때, 의미있고 이해하기 쉬운 오류 메시지를 뿌려주도록 하기 위해 assertNotNull(String, Object)메서드를 사용해보자.
        // assertNotNull(Object)를 사용한다면 JUnit러너는 아무런 메시지도 없이 java.lang.AssertionError의 스택 추적 결과만 뿌려준다.
        // 그렇게 되면 원인 분석에 어려움이 생긴다.
        assertNotNull("Must not return a null response",response);
        // 테스트 결과를 예상 값인 SampleResponse클래스와 비교한다.
        assertEquals("Response should be of type SampleResponse", SampleResponse.class, response.getClass());
    }
}
