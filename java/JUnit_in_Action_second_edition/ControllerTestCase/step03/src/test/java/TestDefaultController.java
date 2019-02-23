import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/* 테스트 클래스의 이름에는 Test라는 접두어를 붙인다
* 빌드 스크립트에서 테스트를 조직하거나 필터링하기 쉬워진다. */
public class TestDefaultController {
    private DefaultController controller;
    private Request request;
    private RequestHandler handler;

    // DefaultController 인스턴스를 만들기 위함.
    // @Before 메서드는 각 테스트 메서드 사이에서 호출되는 JUnit의 기본 확장 포인트.
    @Before
    public void instantiate() throws Exception{
        controller=new DefaultController();
        request=new SampleRequest();
        handler=new SampleHandler();
        controller.addHandler(request,handler);
    }

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
        private static final String NAME = "Test";

        // 이제 SampleResponse는 이 메서드를 통해 얻을 수 있는 신원(identity)이 생겼다.
        public String getName(){
            return NAME;
        }

        // 고유의 equals메서드를 갖게 되었으니 테스트 방법에 변화를 줄 수 있다.
        public boolean equals(Object object){
            boolean result = false;
            if ( object instanceof SampleResponse ){
                result = ( (SampleResponse) object).getName().equals(getName());
            }
            return result;
        }

        public int hashCode(){
            return NAME.hashCode();
        }
    }

    /* 테스트 메서드의 이름은 가급적 직관적이어야 한다.
    * @Test 어노테이션을 부여하여 테스트 메서드임을 선언한다. */
    @Test
    public void testAddHandler(){
        // 핸들러를 얻어 새 변수(handler2)에 할당한다.
        RequestHandler handler2 = controller.getHandler(request);
        // 앞서 추가했던 핸들러(handler)와 handler2가 동일한지 단언(assertion)해보자.
        assertSame("Handler we set in controller should be the same handler we get", handler2, handler);
    }

    /* JUnit의 assert* 메서드를 사용할 때는, 항상 첫 파라미터로 String을 받도록 하자.
    *  이 파라미터를 이용하면 assert실패 시 JUnit 테스트 러너가 테스터에게 의미있는 설명을 제공할 것이다. */
    @Test
    public void testProcessRequest(){
        // processRequest메서드를 호출한다.
        Response response = controller.processRequest(request);

        // 반환된 Response객체가 null이 아닌지 검사한다.
        // 뒤이어 Response객체의 getClass() 메서드를 호출해야 하므로 중요한 선행검사다.
        // 테스트가 싫패했을 때, 의미있고 이해하기 쉬운 오류 메시지를 뿌려주도록 하기 위해 assertNotNull(String, Object)메서드를 사용해보자.
        // assertNotNull(Object)를 사용한다면 JUnit러너는 아무런 메시지도 없이 java.lang.AssertionError의 스택 추적 결과만 뿌려준다.
        // 그렇게 되면 원인 분석에 어려움이 생긴다.
        assertNotNull("Must not return a null response",response);
        // 테스트 결과를 예상 값인 SampleResponse클래스와 비교한다.
        //assertEquals("Response should be of type SampleResponse", SampleResponse.class, response.getClass());
        // 올바른 Response 클래스라면 예외 없이 '신원'이라는 공통 속성을 가지고 있어야한다. 를 테스트함.
        assertEquals(new SampleResponse(), response);
    }
}
